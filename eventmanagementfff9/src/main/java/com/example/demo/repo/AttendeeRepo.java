package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Attendee.AttendeeEntity;
import com.example.demo.Attendee.User;

public interface AttendeeRepo extends JpaRepository<AttendeeEntity, Long> {

    List<AttendeeEntity> findByCategory(String category);

    List<AttendeeEntity> findByname(String name);
    public AttendeeEntity findByEmail(String email);

    //void deleteById(Long id);
}
