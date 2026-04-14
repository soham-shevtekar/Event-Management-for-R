package com.example.demo.controller;

import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Display categories

    @GetMapping("/categories")
    public String showCategories(Model model) {
        // Fetch categories and add to the model
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories"; // Make sure this matches the template name
    }
    @PostMapping("/add-category")
    public String addCategory(@RequestParam("categoryName") String categoryName) {
        if (!categoryService.categoryExists(categoryName)) {
            categoryService.addCategory(categoryName);
        }
        return "redirect:/categories"; // Redirect to the categories list page
    }
}
