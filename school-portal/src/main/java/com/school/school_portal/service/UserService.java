package com.school.school_portal.service;

import com.school.school_portal.entity.User;
import com.school.school_portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, EmailService emailService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
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

        String rawPassword = generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        user.setPassword(encodedPassword);
        userRepository.save(user);

        String emailSubject = "Account Created";
        String emailBody = "Hello " + user.getRole().getName() + " " + user.getFullName() + ",\n\n" +
                "Your school account has been created. Your password is: " + rawPassword + "\n\n" +
                "Please login to the system and change your password.\n\n" +
                "Regards,\n" +
                "Spring School Office";

        emailService.sendEmail(user.getEmail(), emailSubject, emailBody);
    }

}