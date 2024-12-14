package com.school.school_portal.service;

import com.school.school_portal.dto.ClassForm;
import com.school.school_portal.entity.Teacher;
import com.school.school_portal.entity.Class;
import com.school.school_portal.entity.User;
import com.school.school_portal.repository.ClassRepository;
import com.school.school_portal.repository.StudentRepository;
import com.school.school_portal.repository.TeacherRepository;
import com.school.school_portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassService {

    private final TeacherRepository teacherRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public ClassService(TeacherRepository teacherRepository, ClassRepository classRepository, UserRepository userRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.classRepository = classRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    public List<String> getAllClassesSubjects() {
        return Arrays.asList("Natural Science", "Social Science", "Computer Science", "Economics");
    }

    public List<String> getAllYears() {
        return Arrays.asList("V", "VI", "VII", "VIII", "IX", "X", "XI", "XII");
    }

    public List<String> getAllSections() {
        return Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H");
    }

    public List<String> getAllAvailableTutors() {

        List<Teacher> allTeachers = teacherRepository.findAll();
        List<Teacher> assignedTutors = classRepository.findAll().stream().map(Class::getTutor).toList();

        return allTeachers.stream().filter(teacher -> !assignedTutors.contains(teacher)).map(teacher ->
                teacher.getUser().getFullName()).collect(Collectors.toList());
    }

    public void saveClass(ClassForm classForm) {

        Class newClass = new Class();

        newClass.setSubject(classForm.getSubject());
        newClass.setYear(classForm.getYear());
        newClass.setSection(classForm.getSection());

        String[] nameParts = classForm.getTutor().split(" ");
        String lastName = nameParts[nameParts.length - 1];
        String firstName = String.join(" ", Arrays.copyOf(nameParts, nameParts.length - 1));

        teacherRepository.findByUser_FirstNameAndUser_LastName(firstName, lastName).ifPresent(newClass::setTutor);

        classRepository.save(newClass);
    }

    public List<Class> getClassesForCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User currentUser = userRepository.findByEmail(userDetails.getUsername()).orElse(null);

            if (currentUser != null) {

                if (currentUser.getRole().getName().equals("Admin")) {

                    return classRepository.findAll();
                } else if (currentUser.getRole().getName().equals("Teacher")) {

                    List<Class> classes = classRepository.findByTeacherUserEmail(currentUser.getEmail());
                    classes.addAll(classRepository.findByTutorUserEmail(currentUser.getEmail()));

                    return classes.stream().distinct().collect(Collectors.toList());
                }
            }
        }

        return Collections.emptyList();
    }

    public int getNumberOfStudentsInClass(Integer classId) {

        return studentRepository.countByClassField_Id(classId);
    }

    public Class getClassById(Integer id) {
        return classRepository.findById(id).orElse(null);
    }
}
