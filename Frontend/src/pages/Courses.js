import React, { useEffect, useState } from "react";
import { fetchCourses, enrollCourse } from "../api/api";
import "../styles/Courses.css";
import { useNavigate } from "react-router-dom";

function Courses() {
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [enrollingIds, setEnrollingIds] = useState([]); // ids currently being enrolled
    const [enrolledIds, setEnrolledIds] = useState([]); // ids successfully enrolled in this session
    const navigate = useNavigate();

    useEffect(() => {
        loadCourses();
    }, []);

    async function loadCourses() {
        try {
            setLoading(true);
            setError(null);
            const res = await fetchCourses();
            setCourses(res.data || res || []);
        } catch (err) {
            // store raw server object (if available) or a string message
            const server = err?.response?.data;
            setError(server ?? (err?.message || "Failed to load courses"));
        } finally {
            setLoading(false);
        }
    }

    async function handleEnroll(courseId) {
        const userId = localStorage.getItem("userId") || null;
        if (!userId) {
            alert("Please log in to enroll.");
            navigate("/login");
            return;
        }

        if (enrollingIds.includes(courseId) || enrolledIds.includes(courseId)) return;

        setEnrollingIds((ids) => [...ids, courseId]);
        try {
            await enrollCourse({ userId: Number(userId), courseId });
            setEnrolledIds((ids) => [...ids, courseId]);
            alert("Successfully enrolled in the course!");
        } catch (err) {
            // keep server error object for safe rendering if needed
            const server = err?.response?.data;
            setError(server ?? (err?.message || "Enrollment failed"));
            console.error("Enrollment failed:", err);
            // show a friendly alert too
            alert("Enrollment failed: " + (server ? (server.error || JSON.stringify(server)) : err.message));
        } finally {
            setEnrollingIds((ids) => ids.filter((id) => id !== courseId));
        }
    }

    // Loading state
    if (loading) return <div className="page-container">Loading courses…</div>;

    // Safe error rendering: handles string, object, array
    if (error) {
        const message =
            typeof error === "string"
                ? error
                : Array.isArray(error)
                    ? JSON.stringify(error, null, 2)
                    : JSON.stringify(error, Object.getOwnPropertyNames(error), 2);
        return (
            <div className="page-container">
                <h3>Error loading courses</h3>
                <pre style={{ whiteSpace: "pre-wrap", wordBreak: "break-word" }}>{message}</pre>
            </div>
        );
    }

    return (
        <div className="page-container">
            <h2>Available Courses</h2>

            <div className="courses-grid">
                {courses.length === 0 ? (
                    <p>No courses available.</p>
                ) : (
                    courses.map((course) => {
                        const id = course.id ?? course.courseId; // support different id fields
                        const isEnrolling = enrollingIds.includes(id);
                        const isEnrolled = enrolledIds.includes(id);

                        return (
                            <div key={id} className="course-card">
                                <h3>{course.courseName || course.title || course.name}</h3>
                                <p>
                                    <strong>Code:</strong> {course.courseCode || course.code}
                                </p>
                                <p>{(course.courseDescription || course.description || "").slice(0, 120)}</p>
                                <p>
                                    <strong>Duration:</strong> {course.courseDuration || course.duration}
                                </p>

                                <div style={{ marginTop: 8 }}>
                                    <button
                                        className="enroll-btn"
                                        onClick={() => handleEnroll(id)}
                                        disabled={isEnrolling || isEnrolled}
                                    >
                                        {isEnrolled ? "Enrolled" : isEnrolling ? "Enrolling..." : "Enroll"}
                                    </button>
                                </div>
                            </div>
                        );
                    })
                )}
            </div>
        </div>
    );
}

export default Courses;
