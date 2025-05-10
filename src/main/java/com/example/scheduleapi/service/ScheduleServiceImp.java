package com.example.scheduleapi.service;

import com.example.scheduleapi.dto.ScheduleRequestDto;
import com.example.scheduleapi.dto.ScheduleResponseDto;
import com.example.scheduleapi.entity.Schedule;
import com.example.scheduleapi.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleServiceImp implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImp(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {

        // 요청받은 데이터로 Schedule 객체 생성
        Schedule schedule = new Schedule(requestDto.getTask(), requestDto.getAuthor(), requestDto.getPassword());

        return scheduleRepository.saveschedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> fineSchedule(String author, LocalDate updatedDate) {

        return scheduleRepository.findSchedule(author, updatedDate);
    }
}
