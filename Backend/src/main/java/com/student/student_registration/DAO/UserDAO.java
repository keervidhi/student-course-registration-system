package com.student.student_registration.DAO;

import com.student.student_registration.model.User;
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
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final BeanPropertyRowMapper<User> ROW_MAPPER =
            new BeanPropertyRowMapper<>(User.class);

    public Long save(User user) {
        final String sql = "INSERT INTO users (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return (key != null) ? key.longValue() : null;
    }

    public int update(User user) {
        final String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getId());
    }

    public int deleteById(Integer id) {
        final String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Optional<User> findById(Integer id) {
        final String sql = "SELECT id, first_name AS firstName, last_name AS lastName, email, password FROM users WHERE id = ?";
        List<User> list = jdbcTemplate.query(sql, ROW_MAPPER, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public Optional<User> findByEmail(String email) {
        final String sql = "SELECT id, first_name AS firstName, last_name AS lastName, email, password FROM users WHERE email = ?";
        List<User> list = jdbcTemplate.query(sql, ROW_MAPPER, email);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public List<User> findAll() {
        final String sql = "SELECT id, first_name AS firstName, last_name AS lastName, email, password FROM users";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public long count() {
        final String sql = "SELECT COUNT(*) FROM users";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}

