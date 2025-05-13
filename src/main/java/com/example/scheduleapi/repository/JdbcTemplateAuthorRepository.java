package com.example.scheduleapi.repository;

import com.example.scheduleapi.common.exception.AuthorNotFoundException;
import com.example.scheduleapi.entity.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateAuthorRepository implements AuthorRepository{

    private static JdbcTemplate jdbcTemplate;

    public JdbcTemplateAuthorRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 작성자 생성
    @Override
    public Author saveAuthor(Author author) {
        // SimpleJdbcInsert 객체 생성 (INSERT Query를 직접 작성하지 않아도 되도록)
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("authors")
                .usingGeneratedKeyColumns("author_id")
                // 입력할 컬럼만 명시 (not null 발생을 막기 위해)
                .usingColumns("name", "email");

        // DB에 넣을 데이터 파라미터 설정 (키-컬럼이름, 값-값)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", author.getName());
        parameters.put("email", author.getEmail());

        // 저장 후 생성된 key값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return findAuthorById(key.longValue());
    }

    // 선택 작성자 조회
    @Override
    public Author findAuthorById(Long authorId) {

        List<Author> result = jdbcTemplate.query("SELECT * FROM authors WHERE author_id = ?", authorRowMapper(), authorId);

        return result.stream().findAny().orElseThrow(() -> new AuthorNotFoundException());
    }

    // 이름과 이메일로 작성자 조회
    // 단, 존재하지 않는다면 생성
    @Override
    public Author findOrSaveAuthor(String name, String email) {
        List<Author> result = jdbcTemplate.query("SELECT * FROM authors WHERE name = ? AND email = ?", authorRowMapper(), name, email);

        return result.stream().findAny().orElseGet(() -> saveAuthor(new Author(name, email)));
    }

    private RowMapper<Author> authorRowMapper() {
        return new RowMapper<Author>() {
            @Override
            public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Author(
                        rs.getLong("author_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    }
}
