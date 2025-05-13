package com.example.scheduleapi.service;

import com.example.scheduleapi.common.exception.IncorrectPasswordException;
import com.example.scheduleapi.dto.SchedulePasswordDto;
import com.example.scheduleapi.dto.ScheduleRequestDto;
import com.example.scheduleapi.dto.ScheduleResponseDto;
import com.example.scheduleapi.entity.Author;
import com.example.scheduleapi.entity.Schedule;
import com.example.scheduleapi.repository.AuthorRepository;
import com.example.scheduleapi.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImp implements ScheduleService{

    private final ScheduleRepository scheduleRepository;
    private final AuthorRepository authorRepository;

    public ScheduleServiceImp(ScheduleRepository scheduleRepository, AuthorRepository authorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.authorRepository = authorRepository;
    }

    // 일정 생성
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        // 해당 이름과 이메일이 일치하는 기존 작성자가 있는지 확인 후,
        // 없다면 생성하여 / 있다면 조회하여 불러오기
        Author author = authorRepository.findOrSaveAuthor(requestDto.getName(), requestDto.getEmail());

        // 요청받은 데이터로 Schedule 객체 생성
        Schedule schedule = new Schedule(requestDto.getTask(), author.getAuthorId(), requestDto.getPassword());

        // 생성한 schedule로 다시 받기
        return ScheduleToResponseDto(scheduleRepository.saveschedule(schedule));
    }

    // 일정 조회 (페이지네이션)
    @Override
    public List<ScheduleResponseDto> findSchedulePage(int page, int size) {
        // Schedule 조회
        // 단, offset부터 size 크기만 조회
        int offset = page * size;
        List<Schedule> scheduleList = scheduleRepository.findSchedulePage(offset, size);

        List<ScheduleResponseDto> responseDtoList = new ArrayList<>();
        for (Schedule s : scheduleList) {
            responseDtoList.add(ScheduleToResponseDto(s));
        }

        return responseDtoList;
    }

    // 전체 일정 조회
    @Override
    public List<ScheduleResponseDto> findSchedule(Long authorId, LocalDate updatedDate) {

        // Schedule 전체 조회하기
        List<Schedule> scheduleList = scheduleRepository.findSchedule(authorId, updatedDate);

        List<ScheduleResponseDto> responseDtoList = new ArrayList<>();
        for (Schedule s : scheduleList) {
            responseDtoList.add(ScheduleToResponseDto(s));
        }

        return responseDtoList;
    }

    // 선택 일정 조회
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        // id를 기준으로 조회
        return ScheduleToResponseDto(scheduleRepository.findScheduleById(id));
    }

    // 선택 일정 수정
    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        // 필수값 검증
        if (requestDto.getTask() == null){ // || requestDto.getAuthorId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The task and author are required values.");
        }

        // 수정할 일정 비밀번호 검증
        validatePassword(id, requestDto.getPassword());

        // 해당 이름과 이메일이 일치하는 기존 작성자가 있는지 확인 후,
        // 없다면 생성하여 / 있다면 조회하여 불러오기
        Author author = authorRepository.findOrSaveAuthor(requestDto.getName(), requestDto.getEmail());

        // 일정 수정
        int updatedRow = scheduleRepository.updateSchedule(id, requestDto.getTask(), author.getAuthorId());

        // 수정된 일정이 없다면 예외처리
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been modified.");
        }

        // 수정된 메모 조회
        return ScheduleToResponseDto(scheduleRepository.findScheduleById(id));
    }

    // 선택 일정 삭제
    @Override
    public void deleteSchedule(Long id, SchedulePasswordDto passwordDto) {
        // 수정할 일정 비밀번호 검증
        validatePassword(id, passwordDto.getPassword());

        // 일정 삭제
        int deletedRow = scheduleRepository.deleteSchedule(id);

        // 삭제된 일정이 없다면 예외처리
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }

    // Schedule을 받으면 ScheduleResponseDto 형태로 변형
    // Schedule의 authorId로 name 찾아 매치
    private ScheduleResponseDto ScheduleToResponseDto(Schedule schedule) {
        // author_id와 매치되는 author 찾기
        Author author = authorRepository.findAuthorById(schedule.getAuthorId());

        return new ScheduleResponseDto(schedule, author.getName());
    }

    // 비밀번호 검증
    private void validatePassword(Long id, String password) {
        // 수정할 일정 비밀번호 확인
        // - id를 기준으로 조회
        Schedule schedule = scheduleRepository.findScheduleById(id);

        // - 비밀번호가 틀렸을 경우 예외 처리
        if (!password.equals(schedule.getPassword())) {
            throw new IncorrectPasswordException();
        }
    }
}
