package com.school.school_portal.controller;

import com.school.school_portal.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

/*
    @RequestMapping("/")
    public String sendEmail(){
        emailService.sendEmail(emailUsername, "Test", "Test");
        return "Email sent";
    }

 */
}
