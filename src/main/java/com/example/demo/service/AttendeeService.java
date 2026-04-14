package com.example.demo.service;

import com.example.demo.Attendee.AttendeeEntity;
import com.example.demo.repo.AttendeeRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendeeService {

    @Autowired
    private AttendeeRepo attendeeRepository;

    // Get all attendees
    public List<AttendeeEntity> getAllAttendees() {
        return attendeeRepository.findAll();
    }

    // Add a new attendee
    public void addAttendee(AttendeeEntity attendee) {
        attendeeRepository.save(attendee);
    }

    // Find attendee by ID
    public Optional<AttendeeEntity> getAttendeeById(Long id) {
        return attendeeRepository.findById(id);
    }

    // Update an existing attendee
    public void updateAttendee(Long id, AttendeeEntity updatedAttendee) {
        Optional<AttendeeEntity> attendeeOpt = attendeeRepository.findById(id);
        if (attendeeOpt.isPresent()) {
            AttendeeEntity existingAttendee = attendeeOpt.get();
            existingAttendee.setName(updatedAttendee.getName());
            existingAttendee.setEmail(updatedAttendee.getEmail());
            existingAttendee.setContact(updatedAttendee.getContact());
            existingAttendee.setCategory(updatedAttendee.getCategory());
            existingAttendee.setDob(updatedAttendee.getDob());
            existingAttendee.setAge(updatedAttendee.getAge());
            existingAttendee.setAddress(updatedAttendee.getAddress());
            attendeeRepository.save(existingAttendee);
        }
    }

    // Delete attendee by ID
    
    public void deleteAttendeeById(Long id) {
    	System.out.println("AttendeeServices -- deleteAttendeeById -- b4 delete id="+id);
        attendeeRepository.deleteById(id);
        System.out.println("AttendeeServices -- deleteAttendeeById -- afer delete id="+id);
    }

    // Get all attendees by category
    public List<AttendeeEntity> getAllAttendeesByCategory(String category) {
        // Implementation for fetching attendees by category (if needed)
        return attendeeRepository.findByCategory(category);
    }
}
