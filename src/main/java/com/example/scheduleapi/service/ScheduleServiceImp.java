package com.example.scheduleapi.service;

import com.example.scheduleapi.dto.ScheduleRequestDto;
import com.example.scheduleapi.dto.ScheduleResponseDto;
import com.example.scheduleapi.entity.Schedule;
import com.example.scheduleapi.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        Optional<Schedule> optionalSchedule = scheduleRepository.findScheduleById(id);

        // NPE 방지
        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return new ScheduleResponseDto(optionalSchedule.get());
    }
}
