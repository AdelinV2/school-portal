package com.school.school_portal.controller;

import com.school.school_portal.entity.User;
import com.school.school_portal.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendEmailNewAccount(User user, String password) {

        String emailSubject = "Account Created";

        String emailBody = "Hello " + user.getRole().getName() + " " + user.getFullName() + ",\n\n" +
                "Your school account has been created. Your password is: " + password + "\n\n" +
                "Please login to the system and change your password.\n\n" +
                "Regards,\n" +
                "Spring School Office";

        emailService.sendEmail(user.getEmail(), emailSubject, emailBody);
    }

    public void sendEmailResetPassword(User user, String password) {
        String emailSubject = "Password Reset";

        String emailBody = "Hello " + user.getRole().getName() + " " + user.getFullName() + ",\n\n" +
                "Your password has been reset. Your new password is: " + password + "\n\n" +
                "Please login to the system and change your password.\n\n" +
                "Regards,\n" +
                "Spring School Office";

        emailService.sendEmail(user.getEmail(), emailSubject, emailBody);
    }
}
