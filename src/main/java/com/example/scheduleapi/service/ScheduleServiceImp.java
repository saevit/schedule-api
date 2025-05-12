package com.example.scheduleapi.service;

import com.example.scheduleapi.dto.SchedulePasswordDto;
import com.example.scheduleapi.dto.ScheduleRequestDto;
import com.example.scheduleapi.dto.ScheduleResponseDto;
import com.example.scheduleapi.entity.Schedule;
import com.example.scheduleapi.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleServiceImp implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImp(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 일정 생성
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {

        // 요청받은 데이터로 Schedule 객체 생성
        Schedule schedule = new Schedule(requestDto.getTask(), requestDto.getAuthor(), requestDto.getPassword());

        return scheduleRepository.saveschedule(schedule);
    }

    // 전체 일정 조회
    @Override
    public List<ScheduleResponseDto> fineSchedule(String author, LocalDate updatedDate) {

        return scheduleRepository.findSchedule(author, updatedDate);
    }

    // 선택 일정 조회
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        // id를 기준으로 조회
        Schedule schedule = scheduleRepository.findScheduleById(id);

        return new ScheduleResponseDto(schedule);
    }

    // 선택 일정 수정
    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        // 필수값 검증
        if (requestDto.getTask() == null || requestDto.getAuthor() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The task and author are required values.");
        }

        // 수정할 일정 비밀번호 검증
        validatePassword(id, requestDto.getPassword());

        // 일정 수정
        int updatedRow = scheduleRepository.updateSchedule(id, requestDto.getTask(), requestDto.getAuthor());

        // 수정된 일정이 없다면 예외처리
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been modified.");
        }

        // 수정된 메모 조회
        return new ScheduleResponseDto(scheduleRepository.findScheduleById(id));
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

    private void validatePassword(Long id, String password){
        // 수정할 일정 비밀번호 확인
        // - id를 기준으로 조회
        Schedule schedule = scheduleRepository.findScheduleById(id);

        // - 비밀번호가 틀렸을 경우 예외 처리
        if (!password.equals(schedule.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password is incorrect.");
        }
    }
}
