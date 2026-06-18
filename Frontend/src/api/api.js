// src/api/api.js
import axios from "axios";

const API_BASE = process.env.REACT_APP_API_BASE || "http://localhost:8082";
// We set axios baseURL to the backend root (without duplicating /api)
const api = axios.create({
    baseURL: `${API_BASE}/api`, // final requests use e.g. http://localhost:8082/api/...
    headers: {
        "Content-Type": "application/json",
    },
    withCredentials: false,
});

// Add auth token automatically if present
api.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");
    if (token) {
        config.headers = config.headers || {};
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

// Courses
export const fetchCourses = () => api.get("/courses");
export const fetchCourse = (id) => api.get(`/courses/${id}`);
export const createCourse = (data) => api.post("/courses", data);
export const updateCourse = (id, data) => api.put(`/courses/${id}`, data);
export const removeCourse = (id) => api.delete(`/courses/${id}`);

// Users
export const fetchUsers = () => api.get("/users");
export const fetchUser = (id) => api.get(`/users/${id}`);
export const registerUser = (user) => api.post("/users/register", user);

// Registrations
export const fetchRegistrations = () => api.get("/registrations");
export const fetchRegistrationsByUser = (userId) => api.get(`/registrations?userId=${userId}`);
export const createRegistration = (payload) => api.post("/registrations", payload);
export const deleteRegistration = (id) => api.delete(`/registrations/${id}`);

// Enroll a course
// NOTE: use this function from frontend. It expects a single object payload { userId, courseId }.
// It posts to /api/registrations (no duplicate /api).
export const enrollCourse = (payload) => {
    return api.post("/registrations", payload);
};

export const fetchAllRegistrations = () => api.get("/registrations/all");

// Unenroll a user from a course
export const unenrollCourse = (userId, courseId) => {
    return api.delete("/registrations", { params: { userId, courseId } });
};


export default api;
