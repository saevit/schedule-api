package com.example.scheduleapi.repository;

import com.example.scheduleapi.dto.ScheduleResponseDto;
import com.example.scheduleapi.entity.Schedule;

import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveschedule(Schedule schedule);

    Optional<Schedule> findScheduleById(Long id);
}
