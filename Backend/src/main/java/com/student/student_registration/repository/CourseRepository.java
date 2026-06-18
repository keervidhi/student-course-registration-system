package com.student.student_registration.repository;

import com.student.student_registration.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Courses, Integer> {
    // custom queries if needed
}

