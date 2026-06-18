import React, { useEffect, useState } from "react";
import { fetchAllRegistrations } from "../api/api";

export default function AllRegistrations() {
    const [rows, setRows] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => { load(); }, []);

    async function load() {
        try {
            setLoading(true);
            const res = await fetchAllRegistrations();
            setRows(res.data || []);
        } catch (err) {
            setError(err.response?.data || err.message || "Failed to load");
        } finally {
            setLoading(false);
        }
    }

    if (loading) return <div className="page-container">Loading registrations…</div>;
    if (error) return <div className="page-container">Error: {String(error)}</div>;

    return (
        <div className="page-container">
            <h2>All Registrations (Admin)</h2>
            {rows.length === 0 ? (
                <p>No registrations yet.</p>
            ) : (
                <table className="data-table" style={{width:"100%", borderCollapse:"collapse"}}>
                    <thead>
                    <tr>
                        <th style={{padding:8, borderBottom:"1px solid #ddd"}}>Reg ID</th>
                        <th style={{padding:8, borderBottom:"1px solid #ddd"}}>Date</th>
                        <th style={{padding:8, borderBottom:"1px solid #ddd"}}>User</th>
                        <th style={{padding:8, borderBottom:"1px solid #ddd"}}>Email</th>
                        <th style={{padding:8, borderBottom:"1px solid #ddd"}}>Course</th>
                        <th style={{padding:8, borderBottom:"1px solid #ddd"}}>Code</th>
                    </tr>
                    </thead>
                    <tbody>
                    {rows.map(item => (
                        <tr key={item.registrationId ?? item.registration_id ?? `${item.userId}-${item.courseId}`}>
                            <td style={{padding:8, borderBottom:"1px solid #eee"}}>{item.registrationId ?? item.registration_id ?? item.id}</td>
                            <td style={{padding:8, borderBottom:"1px solid #eee"}}>{item.createdAt ? new Date(item.createdAt).toLocaleString() : (item.created_at ? new Date(item.created_at).toLocaleString() : "—")}</td>
                            <td style={{padding:8, borderBottom:"1px solid #eee"}}>{(item.userFirstName ?? item.first_name ?? "") + " " + (item.userLastName ?? item.last_name ?? "")}</td>
                            <td style={{padding:8, borderBottom:"1px solid #eee"}}>{item.userEmail ?? item.email}</td>
                            <td style={{padding:8, borderBottom:"1px solid #eee"}}>{item.courseName ?? item.course_name}</td>
                            <td style={{padding:8, borderBottom:"1px solid #eee"}}>{item.courseCode ?? item.course_code}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}
