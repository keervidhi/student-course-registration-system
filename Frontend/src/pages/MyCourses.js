import React, { useEffect, useState } from "react";
import { fetchRegistrationsByUser, unenrollCourse } from "../api/api";

export default function MyCourses() {
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [unloadingIds, setUnloadingIds] = useState([]); // registering removal in progress

    useEffect(() => {
        const userId = localStorage.getItem("userId");
        if (!userId) {
            setError("Not logged in");
            setLoading(false);
            return;
        }
        load(userId);
    }, []);

    async function load(userId) {
        try {
            setLoading(true);
            const res = await fetchRegistrationsByUser(userId);
            // backend returns courses array in res.data
            setCourses(res.data || []);
        } catch (err) {
            setError(err.response?.data || err.message || "Failed to load");
        } finally {
            setLoading(false);
        }
    }

    async function handleUnenroll(courseId) {
        const userId = localStorage.getItem("userId");
        if (!userId) {
            alert("Please login first");
            return;
        }
        if (unloadingIds.includes(courseId)) return;

        // optimistic UI: remove locally after success
        setUnloadingIds(ids => [...ids, courseId]);
        try {
            const res = await unenrollCourse(Number(userId), courseId);
            // success -> remove from list
            setCourses(prev => prev.filter(c => (c.id ?? c.courseId) !== courseId));
            alert("You have been unenrolled from the course.");
        } catch (err) {
            console.error(err);
            if (err?.response) {
                alert("Unenroll failed: " + JSON.stringify(err.response.data));
            } else {
                alert("Unenroll failed: " + err.message);
            }
        } finally {
            setUnloadingIds(ids => ids.filter(id => id !== courseId));
        }
    }

    if (loading) return <div>Loading your courses…</div>;
    if (error) return <div>Error: {JSON.stringify(error)}</div>;

    return (
        <div className="page-container">
            <h2>My Courses</h2>
            {courses.length === 0 ? (
                <p>You are not enrolled in any courses yet.</p>
            ) : (
                <ul style={{ listStyle: 'none', padding: 0 }}>
                    {courses.map(c => {
                        const id = c.id ?? c.courseId;
                        const isRemoving = unloadingIds.includes(id);
                        return (
                            <li key={id} style={{ marginBottom: 12, display: 'flex', alignItems: 'center', justifyContent: 'space-between', maxWidth: 800 }}>
                                <div>
                                    <strong>{c.courseName || c.course_name}</strong> — {c.courseCode || c.course_code}
                                </div>
                                <div>
                                    <button
                                        onClick={() => handleUnenroll(id)}
                                        disabled={isRemoving}
                                        style={{ padding: '6px 12px', borderRadius: 6, cursor: isRemoving ? 'wait' : 'pointer' }}
                                    >
                                        {isRemoving ? "Removing..." : "Unenroll"}
                                    </button>
                                </div>
                            </li>
                        );
                    })}
                </ul>
            )}
        </div>
    );
}
