package com.student.student_registration.Controller;

import com.student.student_registration.DAO.RegistrationDAO;
import com.student.student_registration.DAO.RegistrationInfo;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class RegistrationController {

    private final RegistrationDAO registrationDAO;

    public RegistrationController(RegistrationDAO registrationDAO) {
        this.registrationDAO = registrationDAO;
    }

    @PostMapping("/registrations")
    public ResponseEntity<?> enroll(@RequestBody Map<String, Integer> body) {
        Integer userId = body.get("userId");
        Integer courseId = body.get("courseId");
        if (userId == null || courseId == null) {
            return ResponseEntity.badRequest().body("userId and courseId required");
        }
        if (registrationDAO.exists(userId, courseId)) {
            return ResponseEntity.status(409).body("Already enrolled");
        }
        registrationDAO.createRegistration(userId, courseId);
        return ResponseEntity.status(201).body("Enrolled successfully");
    }

    @GetMapping("/registrations")
    public ResponseEntity<?> getUserCourses(@RequestParam Integer userId) {
        if (userId == null) return ResponseEntity.badRequest().body("userId required");
        // returns list of Course objects (Course model must match DB columns)
        var courses = registrationDAO.findCoursesByUserId(userId);
        return ResponseEntity.ok(courses);
    }

    // All registrations with joined info (admin / reporting)
    @GetMapping("/registrations/all")
    public ResponseEntity<List<RegistrationInfo>> getAllRegistrations() {
        return ResponseEntity.ok(registrationDAO.findAllRegistrations());
    }

    // Unenroll: DELETE /api/registrations?userId=3&courseId=1
    @DeleteMapping("/registrations")
    public ResponseEntity<?> unenroll(@RequestParam Integer userId, @RequestParam Integer courseId) {
        if (userId == null || courseId == null) {
            return ResponseEntity.badRequest().body("userId and courseId required");
        }
        try {
            int rows = registrationDAO.deleteRegistrationByUserAndCourse(userId, courseId);
            if (rows > 0) {
                return ResponseEntity.ok(Map.of("message", "Unenrolled successfully"));
            } else {
                return ResponseEntity.status(404).body("Registration not found");
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body("DB error");
        }
    }

}
