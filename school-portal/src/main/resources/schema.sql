CREATE DATABASE IF NOT EXISTS school_management;
USE school_management;

CREATE TABLE IF NOT EXISTS roles (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       first_name VARCHAR(30) NOT NULL,
                       last_name VARCHAR(20) NOT NULL,
                       email VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(68) NOT NULL,
                       active BOOLEAN DEFAULT TRUE,
                       role_id INT NOT NULL,
                       FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS teachers (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          user_id INT NOT NULL,
                          specialization VARCHAR(20) NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                          UNIQUE (user_id)
);

CREATE TABLE IF NOT EXISTS classes (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         subject VARCHAR(20),
                         year VARCHAR(4) NOT NULL,
                         section VARCHAR(1) NOT NULL,
                         tutor INT,
                         FOREIGN KEY (tutor) REFERENCES teachers(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS students (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          user_id INT NOT NULL,
                          class_id INT NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                          FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,
                          UNIQUE (user_id)
);

CREATE TABLE IF NOT EXISTS courses (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(20) NOT NULL,
                         description TEXT,
                         teacher_id INT NOT NULL,
                         FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS class_courses (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               class_id INT NOT NULL,
                               course_id INT NOT NULL,
                               FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,
                               FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
                               UNIQUE (class_id, course_id)
);

CREATE TABLE IF NOT EXISTS grades (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        student_id INT NOT NULL,
                        class_course_id INT NOT NULL,
                        grade DECIMAL(4,2) NOT NULL CHECK (grade >= 1.00 AND grade <= 10.00),
                        date_assigned DATE NOT NULL,
                        FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
                        FOREIGN KEY (class_course_id) REFERENCES class_courses(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS absences (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          student_id INT NOT NULL,
                          class_course_id INT NOT NULL,
                          absence_date DATE NOT NULL,
                          excused BOOLEAN DEFAULT FALSE,
                          FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
                          FOREIGN KEY (class_course_id) REFERENCES class_courses(id) ON DELETE CASCADE
);

INSERT INTO roles (name) VALUES ('Admin'), ('Teacher'), ('Student')
ON DUPLICATE KEY UPDATE name=name;

-- Inserting a default admin user (password is 'admin')
INSERT INTO users (first_name, last_name, email, password, role_id)
VALUES ('admin', 'admin', 'admin@admin.com', '$2a$10$YagZ5R4nkqJsrD3yNJi/WeCqtsEiMsBehSUcIQNgFUIIGt2f1Xs9S', 1);

ON DUPLICATE KEY UPDATE email=email;