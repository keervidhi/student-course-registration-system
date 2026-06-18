package com.student.student_registration.model;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "course_name", length = 255)
    private String courseName;

    @Column(name = "course_code", length = 50)
    private String courseCode;

    @Column(name = "course_description", columnDefinition = "TEXT")
    private String courseDescription;

    @Column(name = "course_duration", length = 50)
    private String courseDuration;

    public Courses() {}

    // getters & setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseDescription() { return courseDescription; }
    public void setCourseDescription(String courseDescription) { this.courseDescription = courseDescription; }

    public String getCourseDuration() { return courseDuration; }
    public void setCourseDuration(String courseDuration) { this.courseDuration = courseDuration; }
}
