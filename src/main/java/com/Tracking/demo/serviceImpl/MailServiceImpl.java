package com.Tracking.demo.serviceImpl;

import com.Tracking.demo.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendPasswordMail(String to, String password) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Your Tracking App Login Credentials");

        message.setText(
                "Your account has been created successfully.\n\n" +
                        "Email: " + to + "\n" +
                        "Temporary Password: " + password + "\n\n" +
                        "Please login using these credentials and change your password."
        );

        mailSender.send(message);
    }
}