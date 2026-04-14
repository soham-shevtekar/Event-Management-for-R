/*
 * package com.example.demo.service;
 * 
 * import java.util.List; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.mail.SimpleMailMessage; import
 * org.springframework.mail.javamail.JavaMailSender; import
 * org.springframework.stereotype.Service; import
 * com.example.demo.Attendee.AttendeeEntity; import
 * com.example.demo.repo.AttendeeRepo;
 * 
 * @Service public class EmailService {
 * 
 * @Autowired private JavaMailSender mailSender; // We need this to send emails
 * 
 * @Autowired private AttendeeRepo attendeeRepo;
 * 
 * // Method to find attendees by category public List<AttendeeEntity>
 * getAttendeesByCategory(String category) { return
 * attendeeRepo.findByCategory(category); }
 * 
 * // Method to find attendees by name public List<AttendeeEntity>
 * getAttendeesByName(String name) { return attendeeRepo.findByname(name); }
 * 
 * // Method to send emails to all attendees in the list public void
 * sendEmailsToAttendees(List<AttendeeEntity> attendees) { for (AttendeeEntity
 * attendee : attendees) { String email = attendee.getEmail(); String subject =
 * "Hello " + attendee.getName(); String body = "Dear " + attendee.getName() +
 * ",\n\nWe are glad to have you in our attendee list!\n\nBest Regards,\nYour Team"
 * ;
 * 
 * // Here's where the magic happens - send the email sendEmail(email, subject,
 * body); } }
 * 
 * // Now we add the sendEmail method here! public void sendEmail(String to,
 * String subject, String body) { SimpleMailMessage message = new
 * SimpleMailMessage(); message.setTo(to); message.setSubject(subject);
 * message.setText(body);
 * 
 * mailSender.send(message); // BOOM! 💥 Email sent! } }
 */