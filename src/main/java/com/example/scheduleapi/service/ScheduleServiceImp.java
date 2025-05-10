package com.example.scheduleapi.service;

import com.example.scheduleapi.dto.ScheduleRequestDto;
import com.example.scheduleapi.dto.ScheduleResponseDto;
import com.example.scheduleapi.entity.Schedule;
import com.example.scheduleapi.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

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

        // 임시 DB에 일정 저장
        Schedule saveSchedule = scheduleRepository.saveschedule(schedule);

        return new ScheduleResponseDto(saveSchedule);
    }
}
