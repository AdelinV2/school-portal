package com.school.school_portal.service;

import com.school.school_portal.controller.EmailController;
import com.school.school_portal.entity.User;
import com.school.school_portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

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

    public void saveNewUserWithRandomPassword(User user) {

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new IllegalStateException("Email already taken");
        }

        String rawPassword = generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        user.setPassword(encodedPassword);

        userRepository.save(user);
        emailController.sendEmailNewAccount(user, rawPassword);
    }
}