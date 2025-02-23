package com.school.school_portal.service;

import com.school.school_portal.controller.EmailController;
import com.school.school_portal.dto.StudentForm;
import com.school.school_portal.entity.User;
import com.school.school_portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailController emailController;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, EmailController emailController, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailController = emailController;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateRandomPassword() {

        int length = 10;

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }

    public boolean saveNewUserWithRandomPassword(User user) {

        if (checkEmailExists(user.getEmail())) {
            return false;
        }

        String rawPassword = generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        user.setPassword(encodedPassword);
        user.setFirstName(user.getFirstName().trim());
        user.setLastName(user.getLastName().trim());
        user.setEmail(user.getEmail().toLowerCase());

        userRepository.save(user);
        emailController.sendEmailNewAccount(user, rawPassword);

        return true;
    }

    public boolean checkEmailExists(String email) {

        return userRepository.findByEmail(email.toLowerCase()).isPresent();
    }

    public void updateUser(User user) {

        user.setFirstName(user.getFirstName().trim());
        user.setLastName(user.getLastName().trim());
        user.setEmail(user.getEmail().toLowerCase());

        userRepository.save(user);
    }

    public void deleteUser(Integer userId) {

        userRepository.deleteById(userId);
    }

    public User getUserByEmail(String name) {

        return userRepository.findByEmail(name).orElse(null);
    }

    public void changePassword(String email, String newPassword) {

        User user = getUserByEmail(email);

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setActive(true);

        userRepository.save(user);
    }

    public void resetPassword(String email) {

        String rawPassword = generateRandomPassword();

        userRepository.updatePasswordByEmail(email, passwordEncoder.encode(rawPassword));
        emailController.sendEmailResetPassword(getUserByEmail(email), rawPassword);
    }

    public User getUserByStudentId(Integer studentId) {

        return userRepository.findByStudentId(studentId);
    }
}