package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.demo.Attendee.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    public User findByEmail(String email);
}