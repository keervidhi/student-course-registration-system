package com.student.student_registration.service;

import com.student.student_registration.model.Courses;
import com.student.student_registration.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Courses create(Courses course) {
        return courseRepository.save(course);
    }

    public Optional<Courses> findById(Integer id) {
        return courseRepository.findById(id);
    }

    public List<Courses> findAll() {
        return courseRepository.findAll();
    }

    public Courses update(Courses course) {
        return courseRepository.save(course);
    }

    public void delete(Integer id) {
        courseRepository.deleteById(id);
    }
}
