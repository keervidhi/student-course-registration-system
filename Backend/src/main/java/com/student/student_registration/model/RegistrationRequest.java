package com.student.student_registration.model;

public class RegistrationRequest {
    private Integer userId;
    private Integer courseId;

    public RegistrationRequest() {}

    public RegistrationRequest(Integer userId, Integer courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCourseId() {
        return courseId;
    }
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}

