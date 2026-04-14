package com.example.demo.controller;

import com.example.demo.Attendee.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public String signupUser(@RequestParam("username") String username,
                             @RequestParam("password") String password) {
        
        if (userRepository.findByUsername(username).isPresent()) {
            return "redirect:/?signupError=true";
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole("ROLE_USER");
        
        userRepository.save(newUser);

        return "redirect:/?signupSuccess=true";
    }
}
