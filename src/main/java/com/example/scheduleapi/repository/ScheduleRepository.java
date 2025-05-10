package com.example.scheduleapi.repository;

import com.example.scheduleapi.dto.ScheduleResponseDto;
import com.example.scheduleapi.entity.Schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveschedule(Schedule schedule);

    List<ScheduleResponseDto> findSchedule(String author, LocalDate updatedDate);

    Optional<Schedule> findScheduleById(Long id);
}
