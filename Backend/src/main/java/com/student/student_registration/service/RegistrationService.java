package com.student.student_registration.service;

import com.student.student_registration.model.Courses;
import com.student.student_registration.model.Registration;
import com.student.student_registration.model.User;
import com.student.student_registration.repository.CourseRepository;
import com.student.student_registration.repository.RegistrationRepository;
import com.student.student_registration.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public RegistrationService(RegistrationRepository registrationRepository,
                               UserRepository userRepository,
                               CourseRepository courseRepository) {
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public Registration register(Integer userId, Integer courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Courses course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Registration reg = new Registration();
        reg.setUser(user);
        reg.setCourse(course);
        return registrationRepository.save(reg);
    }

    public Optional<Registration> findById(Integer id) {
        return registrationRepository.findById(id);
    }

    public List<Registration> findAll() {
        return registrationRepository.findAll();
    }

    public List<Registration> findByUserId(Integer userId) {
        return registrationRepository.findByUserId(userId);
    }

    public List<Registration> findByCourseId(Integer courseId) {
        return registrationRepository.findByCourseId(courseId);
    }

    public void delete(Integer id) {
        registrationRepository.deleteById(id);
    }
}
