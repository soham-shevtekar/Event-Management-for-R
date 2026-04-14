package com.example.demo.controller;

import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Attendee.AttendeeEntity;
import com.example.demo.Attendee.User;
import com.example.demo.repo.AttendeeRepo;
import com.example.demo.repo.UserRepository;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class EmailController {

	@Autowired
	private AttendeeRepo attendeerepo;
    private final JavaMailSender mailSender;

    public EmailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RequestMapping("/send-email")
    public String sendEmail() {
        try {
            List<String> emailAddresses = attendeerepo.findAll()
                                                        .stream()
                                                        .map(AttendeeEntity::getEmail)
                                                        .collect(Collectors.toList());

            for (String email : emailAddresses) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("shevtekarsoham29@gmail.com");
                message.setTo(email);
                message.setSubject("Simple test email from GC!");
                message.setText("This is a sample email body for my first email!");

                mailSender.send(message);
            }
            return "admin-dashboard";
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    @RequestMapping("/send-email-with-attachment")
    public String sendEmailWithAttachment() {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("tutorial.genuinecoder@gmail.com");
            helper.setTo("tutorial.genuinecoder@gmail.com");
            helper.setSubject("Java email with attachment | From GC");
            helper.setText("Please find the attached documents below");

            helper.addAttachment("logo.png", new File("C:\\Users\\Genuine Coder\\Documents\\Attachments\\logo.png"));
            helper.addAttachment("presentation.pptx", new File("C:\\Users\\Genuine Coder\\Documents\\Attachments\\presentation.pptx"));

            mailSender.send(message);
            return "success!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/send-html-email-to-attendees")
    public String sendHtmlEmailToAttendees() {
        try {
            List<String> emailAddresses = attendeerepo.findAll()
                                                       .stream()
                                                       .map(AttendeeEntity::getEmail)
                                                       .collect(Collectors.toList());

            for (String email : emailAddresses) {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setFrom("shevtekarsoham29@gmail.com");
                helper.setTo(email);
                helper.setSubject("Java email with HTML template | From GC");

                // Load the HTML template from resources
                try (var inputStream = Objects.requireNonNull(
                    EmailController.class.getResourceAsStream("/templates/email-content.html"))) {
                    
                    String htmlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    helper.setText(htmlContent, true);
                }

                // Optionally, add inline attachments if needed
//                helper.addInline("logo.png", new File("C:\\Users\\Genuine Coder\\Documents\\Attachments\\logo.png"));

                mailSender.send(message);
            }
            
            return "admin-dashboard";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}