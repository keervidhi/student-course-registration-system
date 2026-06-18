import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Courses from "./pages/Courses";
import MyCourses from "./pages/MyCourses";
import Navbar from "./components/Navbar";
import "./styles/App.css";
import AllRegistrations from "./pages/AllRegistrations";

function App() {
  return (
    <Router>
      <Navbar />
      <div className="app-container">
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/courses" element={<Courses />} />
          <Route path="/mycourses" element={<MyCourses />} />
            <Route path="/all-registrations" element={<AllRegistrations />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;




