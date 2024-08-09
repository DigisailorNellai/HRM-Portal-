package com.example.HRM_Portal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.HRM_Portal.entity.Entity;
import com.example.HRM_Portal.service.EmailService;

@Service
public class MessageProcessingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessingService.class);

    @Autowired
    private EmailService emailService;

    public void processMessage(Entity user) {
        LOGGER.info(String.format("Processing message -> %s", user.toString()));

        try {
            String emailBody = String.format(
                    "Dear %s,\n\nWelcome to our service! Your registration is successful. Your email is: %s and password is: %s",
                    user.getName(), user.getEmail(), user.getPassword()
            );
            String subject = "Registration Successful";
            emailService.sendEmail(user.getEmail(), subject, emailBody);
            LOGGER.info("Email sent successfully to {}", user.getEmail());
        } catch (Exception e) {
            LOGGER.error("Error sending email: {}", e.getMessage());
        }
    }
}