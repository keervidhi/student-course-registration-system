package com.student.student_registration.DAO;

import com.student.student_registration.model.Courses;
import com.student.student_registration.model.Registration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

@Repository
public class RegistrationDAO {

    private final JdbcTemplate jdbcTemplate;

    public RegistrationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Insert a registration (returns number of rows affected)
     */
    public int createRegistration(Integer userId, Integer courseId) {
        String sql = "INSERT INTO registrations (user_id, course_id, created_at) VALUES (?, ?, NOW())";
        return jdbcTemplate.update(sql, userId, courseId);
    }

    // delete registration by userId + courseId
    public int deleteRegistrationByUserAndCourse(Integer userId, Integer courseId) {
        String sql = "DELETE FROM registrations WHERE user_id = ? AND course_id = ?";
        return jdbcTemplate.update(sql, userId, courseId);
    }

    /**
     * Check if a user is already registered for a course
     */
    public boolean exists(Integer userId, Integer courseId) {
        String sql = "SELECT COUNT(*) FROM registrations WHERE user_id = ? AND course_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, courseId);
        return count != null && count > 0;
    }

    /**
     * Returns a list of Course objects that the given user is enrolled in.
     * The Courses model must match the column names (bean property mapping).
     */
    public List<Courses> findCoursesByUserId(Integer userId) {
        String sql = "SELECT c.* FROM courses c JOIN registrations r ON c.id = r.course_id WHERE r.user_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Courses.class), userId);
    }

    /**
     * Returns registrations rows mapped to Registration model.
     * Make sure your Registration model fields match the column names or
     * use appropriate @Column annotations.
     */
    public List<Registration> findByUserId(Integer userId) {
        String sql = "SELECT * FROM registrations WHERE user_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Registration.class), userId);
    }

    /**
     * Full joined query returning DTO RegistrationInfo (reporting).
     */
    public List<RegistrationInfo> findAllRegistrations() {
        String sql = """
            SELECT r.id AS registration_id, r.created_at,
                   u.id AS user_id, u.first_name, u.last_name, u.email,
                   c.id AS course_id, c.course_name, c.course_code
            FROM registrations r
            JOIN users u ON r.user_id = u.id
            JOIN courses c ON r.course_id = c.id
            ORDER BY c.course_name, r.created_at DESC
            """;

        RowMapper<RegistrationInfo> mapper = (ResultSet rs, int rowNum) -> {
            RegistrationInfo info = new RegistrationInfo();
            info.setRegistrationId(rs.getInt("registration_id"));
            info.setCreatedAt(rs.getString("created_at"));
            info.setUserId(rs.getInt("user_id"));
            info.setUserFirstName(rs.getString("first_name"));
            info.setUserLastName(rs.getString("last_name"));
            info.setUserEmail(rs.getString("email"));
            info.setCourseId(rs.getInt("course_id"));
            info.setCourseName(rs.getString("course_name"));
            info.setCourseCode(rs.getString("course_code"));
            return info;
        };

        return jdbcTemplate.query(sql, mapper);
    }


}
