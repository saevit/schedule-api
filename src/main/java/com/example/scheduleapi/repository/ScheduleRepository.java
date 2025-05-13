package com.example.scheduleapi.repository;

import com.example.scheduleapi.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {

    Schedule saveschedule(Schedule schedule);

    List<Schedule> findSchedulePage(int offset, int limit);

    List<Schedule> findSchedule(Long authorId, LocalDate updatedDate);

    Schedule findScheduleById(Long id);

    int updateSchedule(Long id, String task, Long authorId);

    int deleteSchedule(Long id);
}
