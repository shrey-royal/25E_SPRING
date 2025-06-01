package com.royal.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.royal.core.entity.UserEntity;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String homePage() {
		return "home";
	}
	
	@GetMapping("/send")
	public String sendData() {
		return "user";
	}
	
//	@PostMapping("/sent")
//	public String getData(@RequestParam String name, @RequestParam String email) {
//		UserEntity user = new UserEntity(name, email);
//		System.out.println(user);
//		
//		return "home";
//	}
	
	@PostMapping("/sent")
	public String getData(@ModelAttribute("user") UserEntity user, Model model) {
		model.addAttribute("name", user.getName());
		model.addAttribute("email", user.getEmail());
	
		return "showUser";
	}
}
