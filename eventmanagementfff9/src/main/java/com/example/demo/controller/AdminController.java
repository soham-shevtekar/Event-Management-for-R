package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	
	

	 @GetMapping("/")
	    public String index2() {
	        
	        return "index"; // Ensure this matches your template name
	    }
	 
	 
	 @GetMapping("/admin-dashboard")
	    public String adminDashboard2() {
	        
	        return "admin-dashboard"; // Ensure this matches your template name
	    }
	 }								