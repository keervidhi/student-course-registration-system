package com.student.student_registration.DAO;

import com.student.student_registration.model.Courses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class CoursesDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final BeanPropertyRowMapper<Courses> ROW_MAPPER =
            new BeanPropertyRowMapper<>(Courses.class);

    public Integer save(Courses course) {
        final String sql = "INSERT INTO courses (course_name, course_code, course_description, course_duration) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getCourseCode());
            ps.setString(3, course.getCourseDescription());
            ps.setString(4, course.getCourseDuration());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return (key != null) ? key.intValue() : null;
    }

    public int update(Courses course) {
        final String sql = "UPDATE courses SET course_name = ?, course_code = ?, course_description = ?, course_duration = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                course.getCourseName(),
                course.getCourseCode(),
                course.getCourseDescription(),
                course.getCourseDuration(),
                course.getId());
    }

    public int deleteById(Integer id) {
        final String sql = "DELETE FROM courses WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Optional<Courses> findById(Integer id) {
        final String sql = "SELECT id, course_name AS courseName, course_code AS courseCode, course_description AS courseDescription, course_duration AS courseDuration FROM courses WHERE id = ?";
        List<Courses> list = jdbcTemplate.query(sql, ROW_MAPPER, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public List<Courses> findAll() {
        final String sql = "SELECT id, course_name AS courseName, course_code AS courseCode, course_description AS courseDescription, course_duration AS courseDuration FROM courses";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public long count() {
        final String sql = "SELECT COUNT(*) FROM courses";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}
