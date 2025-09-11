package com.bakery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bakery.dto.UserRequest;
import com.bakery.dto.UserResponse;
import com.bakery.service.UserService;

import jakarta.servlet.annotation.MultipartConfig;

@Controller
@RequestMapping("/auth")
@MultipartConfig
public class AuthController {
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new UserRequest());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute("user") UserRequest userRequest,
                               @RequestParam("imageFile") MultipartFile imageFile,
                               Model model) {
        try {
            userService.register(userRequest, imageFile);
            model.addAttribute("message", "Signup successful! Please sign in.");
            return "signin";
        } catch (Exception e) {
            model.addAttribute("error", "Error: " + e.getMessage());
            return "signup";
        }
    }

    @GetMapping("/signin")
    public String signinForm() {
        return "signin";
    }

    @PostMapping("/signin")
    public String signinSubmit(@RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        UserResponse user = userService.login(email, password);
        if (user != null) {
            model.addAttribute("user", user);
            return "welcome";
        }
        model.addAttribute("error", "Invalid credentials!");
        return "signin";
    }
}
