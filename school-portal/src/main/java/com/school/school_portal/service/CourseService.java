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
    private final TeacherService teacherService;
    private final ClassCourseRepository classCourseRepository;
    private final ClassRepository classRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, TeacherService teacherService, ClassCourseRepository classCourseRepository, ClassRepository classRepository) {
        this.courseRepository = courseRepository;
        this.teacherService = teacherService;
        this.classCourseRepository = classCourseRepository;
        this.classRepository = classRepository;
    }

    public List<String> getAllCoursesSubjects() {

        return Arrays.asList("English", "Mathematics", "Science", "Social Studies", "Computer Science", "Economics",
                "Accounting", "Biology", "Chemistry", "Physics", "Geography", "History", "Civics", "Physical Education",
                "Art", "Music", "Business Studies", "French", "Spanish", "German");
    }

    public void saveCourse(@Valid CourseForm courseForm, Integer classId) {

        Course newCourse = new Course();

        newCourse.setName(courseForm.getSubject());

        teacherService.getTeacherByFullName(courseForm.getTeacher()).ifPresent(newCourse::setTeacher);

        courseRepository.save(newCourse);

        ClassCourse newClassCourse = new ClassCourse();

        newClassCourse.setClassField(classRepository.findById(classId).get());
        newClassCourse.setCourse(newCourse);

        classCourseRepository.save(newClassCourse);
    }

    public void updateCourse(@Valid CourseForm courseForm, Integer id) {

        Course course = courseRepository.findById(id).get();

        course.setName(courseForm.getSubject());
        teacherService.getTeacherByFullName(courseForm.getTeacher()).ifPresent(course::setTeacher);

        courseRepository.save(course);
    }
}
