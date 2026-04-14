package com.example.demo.service;

import com.example.demo.Attendee.User;

public interface UserServicee {
    void saveUser(User user);
    User findByUsername(String username); // Add this line
    
    public User createUser(User user) ;
    
//    public boolean checkEmail(String email);
    
    
}