// src/pages/Register.js
import React, { useState } from "react";
import "../styles/AuthForm.css";
import { registerUser } from "../api/api";
import { useNavigate } from "react-router-dom";

function Register() {
    const [formData, setFormData] = useState({ firstName: "", lastName: "", email: "", password: "" });
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleChange = (e) => setFormData({ ...formData, [e.target.name]: e.target.value });

    async function handleSubmit(e) {
        e.preventDefault();
        setLoading(true);
        try {
            // backend User entity expects firstName, lastName, email, password
            const payload = {
                firstName: formData.firstName,
                lastName: formData.lastName,
                email: formData.email,
                password: formData.password
            };
            const res = await registerUser(payload);
            alert("Registered successfully (id: " + res.data.id + "). You can now login (if you add login server-side).");
            navigate("/");
        } catch (err) {
            alert("Register failed: " + (err.response?.data || err.message));
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className="auth-container">
            <form className="auth-form" onSubmit={handleSubmit}>
                <h2>Create Account</h2>
                <input name="firstName" placeholder="First Name" value={formData.firstName} onChange={handleChange} required />
                <input name="lastName" placeholder="Last Name" value={formData.lastName} onChange={handleChange} required />
                <input type="email" name="email" placeholder="Email Address" value={formData.email} onChange={handleChange} required />
                <input type="password" name="password" placeholder="Create Password" value={formData.password} onChange={handleChange} required />
                <button type="submit" disabled={loading}>{loading ? "Registering..." : "Register"}</button>
            </form>
        </div>
    );
}

export default Register;
