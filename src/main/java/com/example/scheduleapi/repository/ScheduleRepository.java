package com.example.scheduleapi.repository;

import com.example.scheduleapi.dto.ScheduleResponseDto;
import com.example.scheduleapi.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto saveschedule(Schedule schedule);

    List<ScheduleResponseDto> findSchedule(String author, LocalDate updatedDate);

    Schedule findScheduleById(Long id);

    int updateSchedule(Long id, String task, String author);

    int deleteSchedule(Long id);
}
