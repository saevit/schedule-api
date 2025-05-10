package com.example.scheduleapi.repository;

import com.example.scheduleapi.dto.ScheduleResponseDto;
import com.example.scheduleapi.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository{

    private static JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveschedule(Schedule schedule) {
        // SimpleJdbcInsert 객체 생성 (INSERT Query를 직접 작성하지 않아도 되도록)
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedules")
                .usingGeneratedKeyColumns("id")
                // 입력할 컬럼만 명시 (not null 발생을 막기 위해)
                .usingColumns("task", "author", "password");

        // DB에 넣을 데이터 파라미터 설정 (키-컬럼이름, 값-값)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("task", schedule.getTask());
        parameters.put("author", schedule.getAuthor());
        parameters.put("password", schedule.getPassword());


        // 저장 후 생성된 key값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(findScheduleById(key.longValue()).get());
    }

    @Override
    public List<ScheduleResponseDto> findSchedule(String author, LocalDate updatedDate) {
        // query(String sql, Object[] args, RowMapper<T> rowMapper)
        StringBuilder sql = new StringBuilder("SELECT * FROM schedules WHERE 1=1");
        List<Object> args = new ArrayList<>();

        // 작성자명 조건으로 조회
        if (author != null && !author.isBlank()) {
            sql.append(" AND author = ?");
            args.add(author);
        }

        // 수정일 조건으로 조회
        if (updatedDate != null) {
            sql.append(" AND DATE(updated_at) = ?");
            args.add(updatedDate);
        }

        // 수정일 기준 내림차순으로 정렬
        sql.append(" ORDER BY updated_at DESC");

        return jdbcTemplate.query(sql.toString(), args.toArray(),scheduleRowMapperV1());
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapperV1() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getString("author"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    }


    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        // query(String sql, RowMapper<T> rowMapper, Object args ...)
        List<Schedule> result = jdbcTemplate.query("SELECT * FROM schedules WHERE id = ?", scheduleRowMapperV2(), id);

        return result.stream().findAny();
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getString("author"),
                        rs.getString("password"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    };
}
