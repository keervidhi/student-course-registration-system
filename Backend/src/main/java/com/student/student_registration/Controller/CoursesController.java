package com.student.student_registration.Controller;

import com.student.student_registration.DAO.CoursesDAO;
import com.student.student_registration.model.Courses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CoursesController {

    @Autowired
    private CoursesDAO coursesDAO;

    @GetMapping
    public List<Courses> getAll() {
        return coursesDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Courses> getById(@PathVariable Integer id) {
        Optional<Courses> c = coursesDAO.findById(id);
        return c.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Courses course, UriComponentsBuilder uriBuilder) {
        Integer id = coursesDAO.save(course);
        if (id == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create course");
        URI uri = uriBuilder.path("/api/courses/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).body(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Courses course) {
        course.setId(id);
        int rows = coursesDAO.update(course);
        if (rows == 0) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        int rows = coursesDAO.deleteById(id);
        if (rows == 0) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}

