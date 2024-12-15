package com.school.school_portal.service;

import com.school.school_portal.dto.CourseForm;
import com.school.school_portal.entity.ClassCourse;
import com.school.school_portal.entity.Course;
import com.school.school_portal.repository.ClassCourseRepository;
import com.school.school_portal.repository.ClassRepository;
import com.school.school_portal.repository.CourseRepository;
import com.school.school_portal.repository.TeacherRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final ClassCourseRepository classCourseRepository;
    private final ClassRepository classRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, TeacherRepository teacherRepository, ClassCourseRepository classCourseRepository, ClassRepository classRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.classCourseRepository = classCourseRepository;
        this.classRepository = classRepository;
    }

    public List<String> getAllCoursesSubjects() {

        return Arrays.asList("English", "Mathematics", "Science", "Social Studies", "Computer Science", "Economics",
                "Accounting", "Biology", "Chemistry", "Physics", "Geography", "History", "Civics", "Physical Education",
                "Art", "Music", "Business Studies", "French", "Spanish", "German");
    }

    public List<String> getAllTeachers() {
        return teacherRepository.findAll().stream().map(teacher -> teacher.getUser().getFullName()).toList();
    }

    public void saveCourse(@Valid CourseForm courseForm, Integer classId) {

        Course newCourse = new Course();

        newCourse.setName(courseForm.getSubject());

        String[] teacherNameParts = courseForm.getTeacher().split(" ");
        String teacherLastName = teacherNameParts[teacherNameParts.length - 1];
        String teacherFirstName = String.join(" ", Arrays.copyOf(teacherNameParts, teacherNameParts.length - 1));

        teacherRepository.findByUser_FirstNameAndUser_LastName(teacherFirstName, teacherLastName).ifPresent(newCourse::setTeacher);

        courseRepository.save(newCourse);

        ClassCourse newClassCourse = new ClassCourse();

        newClassCourse.setClassField(classRepository.findById(classId).get());
        newClassCourse.setCourse(newCourse);

        classCourseRepository.save(newClassCourse);
    }
}
