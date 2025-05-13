package com.example.scheduleapi.repository;

import com.example.scheduleapi.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

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

    // 일정 생성
    @Override
    public Schedule saveschedule(Schedule schedule) {
        // SimpleJdbcInsert 객체 생성 (INSERT Query를 직접 작성하지 않아도 되도록)
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedules")
                .usingGeneratedKeyColumns("id")
                // 입력할 컬럼만 명시 (not null 발생을 막기 위해)
                .usingColumns("task", "password", "author_id");

        // DB에 넣을 데이터 파라미터 설정 (키-컬럼이름, 값-값)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("task", schedule.getTask());
        parameters.put("password", schedule.getPassword());
        parameters.put("author_id", schedule.getAuthorId());

        // 저장 후 생성된 key값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return findScheduleById(key.longValue());
    }

    // 전체 일정 조회
    @Override
    public List<Schedule> findSchedule(Long authorId, LocalDate updatedDate) {
        // query(String sql, Object[] args, RowMapper<T> rowMapper)
        StringBuilder sql = new StringBuilder("SELECT * FROM schedules WHERE 1=1");
        List<Object> args = new ArrayList<>();

        // 작성자명 조건으로 조회
        if (authorId != null) {
            sql.append(" AND author_id = ?");
            args.add(authorId);
        }

        // 수정일 조건으로 조회
        if (updatedDate != null) {
            sql.append(" AND DATE(updated_at) = ?");
            args.add(updatedDate);
        }

        // 수정일 기준 내림차순으로 정렬
        sql.append(" ORDER BY updated_at DESC");

        return jdbcTemplate.query(sql.toString(), args.toArray(),scheduleRowMapper());
    }

    // 선택 일정 조회
    @Override
    public Schedule findScheduleById(Long id) {
        // query(String sql, RowMapper<T> rowMapper, Object args ...)
        List<Schedule> result = jdbcTemplate.query("SELECT * FROM schedules WHERE id = ?", scheduleRowMapper(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getLong("author_id"),
                        rs.getString("password"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    };

    // 선택 일정 수정
    @Override
    public int updateSchedule(Long id, String task, Long authorId) {
        // 쿼리의 영향을 받은 row 수를 int로 반환
        return jdbcTemplate.update("UPDATE schedules SET task = ?, author_id = ? WHERE id = ?", task, authorId, id);
    }

    @Override
    public int deleteSchedule(Long id) {
        // 쿼리의 영향을 받은 row 수를 int로 반환
        return jdbcTemplate.update("delete from schedules where id = ?", id);
    }
}
