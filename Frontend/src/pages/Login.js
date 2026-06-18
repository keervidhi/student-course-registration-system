// src/pages/Login.js
import React, { useState } from "react";
import "../styles/AuthForm.css";
import { fetchUsers } from "../api/api";
import { useNavigate } from "react-router-dom";

function Login() {
    const [credentials, setCredentials] = useState({ email: "", password: "" });
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleChange = (e) => setCredentials({ ...credentials, [e.target.name]: e.target.value });

    async function handleSubmit(e) {
        e.preventDefault();
        setLoading(true);
        try {
            // temporary demo: find user by email from GET /api/users
            const res = await fetchUsers();

            const user = res.data.find(u => u.email === credentials.email);
            if (!user) {
                alert("User not found. Please register first.");
                setLoading(false);
                return;
            }
            // WARNING: password is hashed on backend so we cannot validate here.
            // For real login implement server-side /auth/login which returns JWT/session.
            localStorage.setItem("userId", user.id);
            alert("Logged in (demo). UserId stored in localStorage.");
            navigate("/courses");
        } catch (err) {
            alert("Login failed: " + (err.response?.data || err.message));
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className="auth-container">
            <form className="auth-form" onSubmit={handleSubmit}>
                <h2>Welcome Back</h2>
                <input type="email" name="email" placeholder="Email Address" value={credentials.email} onChange={handleChange} required />
                <input type="password" name="password" placeholder="Enter Password" value={credentials.password} onChange={handleChange} required />
                <button type="submit" disabled={loading}>{loading ? "Logging in..." : "Login (demo)"}</button>
            </form>
        </div>
    );
}

export default Login;
