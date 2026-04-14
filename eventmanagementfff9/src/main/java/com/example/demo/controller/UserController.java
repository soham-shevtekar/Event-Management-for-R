package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserController {
	
	 @GetMapping("/index")
	    public String userAccess() {
	        return "index2";
	    }
	 
	 @GetMapping("/admin-dashboard")
	    public String adminDashboard2() {
	        
	        return "useradmin"; // Ensure this matches your template name
	    }
}
