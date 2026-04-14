package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Attendee.AttendeeEntity;
import com.example.demo.repo.AttendeeRepo;
import com.example.demo.service.AttendeeService;
import com.example.demo.service.CategoryService;

//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.http.HttpServletRequest;


@Controller
public class MainController {

    @Autowired
    private AttendeeRepo attendeeRepo;
    @Autowired 
    private AttendeeService attendeeService; 

    private static final String ERROR_PATH = "/error";

    private static final String UPLOADED_FOLDER = "uploads/";



    @PostMapping("/submit-attendee")
    public String submitAttendee(@RequestParam("name") String name,
                                 @RequestParam("email") String email,
                                 @RequestParam("image") MultipartFile imageFile,
                                 @RequestParam("age") Integer age,
                                 @RequestParam("address") String address,
                                 @RequestParam("contact") String contact,
                                 @RequestParam("category") String category,
                                 @RequestParam("dob") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
                                 Model model) {
        if (imageFile.isEmpty()) {
            model.addAttribute("message", "Please select an image to upload");
            return "attendee-form"; // Return to form with error message
        }

        try {
            // Ensure the upload directory exists
            Files.createDirectories(Paths.get(UPLOADED_FOLDER));

            // Save the uploaded image to the server
            String imageFileName = imageFile.getOriginalFilename(); // Get the image file name
            String imagePath = UPLOADED_FOLDER + imageFileName; // Save relative path
            Path path = Paths.get(imagePath);
            Files.write(path, imageFile.getBytes());
            // Create a new attendee
            AttendeeEntity attendee = new AttendeeEntity();
            attendee.setName(name);
            attendee.setEmail(email);
            attendee.setImagePath(imageFileName); // Set the image path here
            attendee.setAge(age);
            attendee.setAddress(address);
            attendee.setContact(contact);
            attendee.setDob(dob);
            attendee.setCategory(category);

            // Save the attendee in the database
            AttendeeEntity savedAttendee = attendeeRepo.save(attendee);
            
            // Redirect to the details page with the attendee ID
            return "redirect:/attendee-details/" + savedAttendee.getId();

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed to upload the image.");
            return "attendee-form"; // Return to form with error message
        }
    }

//    @GetMapping("/success/{id}")
//    public String showSuccessPage(@PathVariable Long id, Model model) {
//        model.addAttribute("attendeeId", id);
//        return "success"; // This will load success.html
//    }
    @GetMapping("/attendee-details/{id}")
    public String showAttendeeDetails(@PathVariable("id") Long id, Model model) {
        AttendeeEntity attendee = attendeeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid attendee Id:" + id));
        model.addAttribute("attendee", attendee); // This should include all fields
        return "attendee-details";  // Show the details page
    }
    
    @GetMapping("/attendees/category/{category}")
    public String getAttendeesByCategory(@PathVariable String category, Model model) {
        List<AttendeeEntity> attendees = attendeeRepo.findByCategory(category);
        model.addAttribute("attendees", attendees);
        model.addAttribute("category", category);
        return "attendees-list"; // This will load attendees-list.html
    }
    
    @GetMapping("/attendee-update/{id}")
    public String updateAttendeeForm(@PathVariable("id") Long id, Model model) {
        Optional<AttendeeEntity> attendeeOpt = attendeeService.getAttendeeById(id);  // Use the service to fetch attendee by ID
        if (attendeeOpt.isPresent()) {
            model.addAttribute("attendee", attendeeOpt.get());
            return "attendee-form";  // Load the form page to edit the attendee
        } else {
            // Handle the case where the attendee is not found
            model.addAttribute("error", "Attendee not found");
            return "error";  // Redirect to an error page or return an appropriate view
        }
    }
    @GetMapping("/attendees/{id}")
    public String getAttendeeById(@PathVariable("id") Long id, Model model) {
        Optional<AttendeeEntity> attendeeOpt = attendeeService.getAttendeeById(id);

        if (attendeeOpt.isPresent()) {
            model.addAttribute("attendee", attendeeOpt.get());
            return "attendeeDetails";  // Replace with your actual view name
        } else {
            model.addAttribute("error", "Attendee not found");
            return "error";  // Redirect to an error page or return an appropriate view
        }
    }
    
   // ---    /attendees/category/Entertainment/{id}(id=${attendee.id})
    
    @DeleteMapping("/attendees/delete/{id}")
    public String deleteAttendee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    	System.out.println("MainController -- deleteAttendee -- id="+id);
    	System.out.println("MainController -- deleteAttendee -- redirectAttributes="+redirectAttributes);
        attendeeService.deleteAttendeeById(id);
        System.out.println("MainController -- deleteAttendee -- Aftrer call to services=");
        redirectAttributes.addFlashAttribute("message", "Attendee deleted successfully.");
        return "redirect:/attendees/category";
    }
    
    @PostMapping("/attendees/delete/{id}")
    public String myMethod(@PathVariable Long id) {
    	System.out.println("hi.. i m here"+id);
    	 attendeeService.deleteAttendeeById(id);
    	return "redirect:/attendees/category/Entertainment";
    }
    
	
    
//    @GetMapping("attendees/category/delete/{id}")
//	 public String deleteFile(@PathVariable Long id) {
//    	attendeeRepo.deleteById(id);
//	        return "redirect:/attendees/category ";
//	 }
//    

    @GetMapping("/attendee-form")
    public String showAttendeeForm(Model model) {
        model.addAttribute("attendee", new AttendeeEntity());
        return "attendee-form"; // This should match the name of your HTML file (attendee-form.html).
    }	
    
    

    @GetMapping("/discover")
    public String discover() {
       
        return "discover"; // This should match the name of your HTML file (attendee-form.html).
    }
    
    
   
    
}
