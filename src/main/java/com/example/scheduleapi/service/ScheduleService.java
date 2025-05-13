package com.example.scheduleapi.service;

import com.example.scheduleapi.dto.SchedulePasswordDto;
import com.example.scheduleapi.dto.ScheduleRequestDto;
import com.example.scheduleapi.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);

    List<ScheduleResponseDto> findSchedule(int page, int size, Long authorId, LocalDate updatedDate);

    ScheduleResponseDto findScheduleById(Long id);

    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto);

    void deleteSchedule(Long id, SchedulePasswordDto passwordDto);
}
