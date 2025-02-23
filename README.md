# School Portal - Spring Boot Application

**School Portal** is a web-based application developed using **Spring Boot**, **MySQL**, and a simple HTML frontend. This project serves as a management tool for educational institutions, allowing administrators and teachers to manage classes, students, courses, attendance, grades, and more. It is designed with a focus on clean architecture and maintainability.

The application is hosted on **Azure** and can be accessed at:  
[https://spring-school.azurewebsites.net/](https://spring-school.azurewebsites.net/)

You can use the following credentials to log in as an administrator:  
**Email**: admin@admin.com  
**Password**: admin

## Features

### 1. **Student Management**
   - Add, update, and delete student records.
   - Assign students to specific classes and courses.
   - Track student grades and attendance.

### 2. **Teacher Management**
   - Add, update, and delete teacher records.
   - Assign teachers to specific courses.
   - Monitor teacher involvement in various classes.

### 3. **Class Management**
   - Create and manage different classes (e.g., IX-A, X-B).
   - Link teachers and students to the respective classes.
   - Assign subjects and courses to each class.

### 4. **Course Management**
   - Add and manage courses.
   - Assign specific teachers to each course.
   - Define the subjects for the courses.

### 5. **Attendance Management**
   - Track student attendance.
   - Mark absences as excused or unexcused.
   - Generate attendance reports for specific students or classes.

### 6. **Grades Management**
   - Input and manage student grades for different courses.
   - Generate grade reports by student or class.
   
### 7. **Authentication & Authorization**
   - Secure login and registration for admins and teachers.
   - Role-based access control to ensure data privacy and security.

### 8. **Email Notifications**
   - Users will receive emails when an account is created or when the password is reset.

## Technologies Used

- **Spring Boot**: For building the backend and handling requests.
- **MySQL**: For database management, storing user data, classes, courses, grades, and attendance.
- **JPA/Hibernate**: For ORM between the database and application entities.
- **Thymeleaf**: For rendering dynamic HTML content.
- **Spring Security**: For authentication and role-based authorization.
- **Docker**: For containerizing the application.
- **Azure**: For hosting the application.

## Docker Support

This application can also be run using Docker, which simplifies the setup and ensures that the application runs in a consistent environment. 

### Pull the Docker Image

If you've built and pushed the Docker image to a registry (e.g., Docker Hub or a private registry), users can pull the image and run it without needing to configure the application manually.

To pull the image from Docker Hub (assuming it's publicly available):

```bash
docker pull adelinv2/school-portal:latest
```

### Run the Docker container
```
docker run -p 8080:8080 adelinv2/school-portal
```

## Installation

To get this project up and running on your local machine, follow the steps below.

### Prerequisites

- JDK 17 or higher
- MySQL database
- Maven

### Clone the repository

```bash
git clone https://github.com/AdelinV2/school-portal.git
cd school-portal
```
- **Environment Configuration**: Ensure you have the appropriate environment variables set in a .env file or modify the application.yml to reflect your database and other configuration details.
