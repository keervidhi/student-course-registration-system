# Student Course Registration System

A full-stack Student Course Registration System built using Spring Boot, JDBC, MySQL, and React. The application allows students to register, manage courses, and enroll in available courses through a user-friendly interface.

---

## 🚀 Features

### Student Management

* Student Registration
* View Student Details
* Update Student Information
* Delete Student Records

### Course Management

* Add New Courses
* View Available Courses
* Update Course Information
* Delete Courses

### Course Registration

* Enroll Students in Courses
* View Registered Courses
* Manage Student-Course Relationships

### Backend

* Spring Boot REST API
* JDBC Database Connectivity
* MySQL Database Integration
* CRUD Operations
* Layered Architecture (Controller, DAO, Model)

### Frontend

* React.js User Interface
* Axios API Integration
* Responsive Design
* Dynamic Data Rendering

---

## 📁 Project Structure

```text
student-course-registration-system/
│
├── backend/
│   ├── src/main/java/
│   │   ├── controller/
│   │   ├── dao/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── config/
│   │   └── StudentRegistrationApplication.java
│   │
│   └── src/main/resources/
│       └── application.properties
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   └── App.js
│   │
│   └── public/
│
└── README.md
```

---

## 🛠️ Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Web
* JDBC
* Spring Data JPA
* Maven
* MySQL

### Frontend

* React.js
* Axios
* HTML5
* CSS3
* JavaScript

### Tools

* IntelliJ IDEA
* VS Code
* MySQL Workbench
* Postman
* Git & GitHub

---

## 🗄️ Database

Database Name:

```sql
studentdb
```

Main Tables:

```text
users
courses
registrations
```

Relationships:

```text
User (1) ---- (*) Registration (*) ---- (1) Course
```

---

## 🚀 Getting Started

### Prerequisites

* Java 17+
* Node.js 18+
* MySQL Server
* Maven

---

## Backend Setup

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Backend runs on:

```text
http://localhost:9090
```

---

## Frontend Setup

```bash
cd frontend
npm install
npm start
```

Frontend runs on:

```text
http://localhost:3000
```

---

## Database Configuration

Update the `application.properties` file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studentdb
spring.datasource.username=root
spring.datasource.password=your_password

server.port=9090
```

---

## 📡 API Endpoints

### Users

```http
POST   /api/users/register
GET    /api/users
GET    /api/users/{id}
PUT    /api/users/{id}
DELETE /api/users/{id}
```

### Courses

```http
POST   /api/courses
GET    /api/courses
GET    /api/courses/{id}
PUT    /api/courses/{id}
DELETE /api/courses/{id}
```

### Registrations

```http
POST   /api/registrations
GET    /api/registrations
GET    /api/registrations/user/{userId}
GET    /api/registrations/course/{courseId}
DELETE /api/registrations/{id}
```

---

## 🔐 Security

* Spring Security Configuration
* REST API Access Control
* Cross-Origin Resource Sharing (CORS) Support

---

## 🤝 Contributing

1. Fork the repository
2. Create a new branch
3. Commit changes
4. Push changes
5. Create a Pull Request

---

## 📄 License

This project is developed for educational and learning purposes.
