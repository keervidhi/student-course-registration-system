package com.student.student_registration.repository;

import com.student.student_registration.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
    List<Registration> findByUserId(Integer userId);   // Spring Data will implement
    List<Registration> findByCourseId(Integer courseId);
}
