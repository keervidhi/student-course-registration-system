import React from "react";
import { Link, useLocation } from "react-router-dom";
import "../styles/Navbar.css";

function Navbar() {
  const location = useLocation();

  return (
    <nav className="navbar">
      <h2 className="logo">CourseRegistration</h2>
      <div className="nav-links">
        <Link to="/courses" className={location.pathname === "/courses" ? "active" : ""}>Courses</Link>
        <Link to="/mycourses" className={location.pathname === "/mycourses" ? "active" : ""}>My Courses</Link>
        <Link to="/" className={location.pathname === "/" ? "active" : ""}>Login</Link>
          <Link to="/register" className={location.pathname === "/register" ? "active" : ""}>Register</Link>
          <Link to="/all-registrations" className={location.pathname === "/all-registrations" ? "active" : ""}>All Registrations</Link>
      </div>
    </nav>
  );
}

export default Navbar;
