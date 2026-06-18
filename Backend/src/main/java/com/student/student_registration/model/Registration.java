package com.student.student_registration.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "registrations", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "course_id"})
})
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Courses course;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    // 🟢 Add these getters and setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
