package com.royal.core.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.royal.core.entity.User;
import com.royal.core.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService service;
	
	@PostMapping
	public ResponseEntity<User> save(@RequestPart("user") String userJson, @RequestPart(value = "file", required = false) MultipartFile file) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			User user = mapper.readValue(userJson, User.class);
			
			if(file != null && !file.isEmpty()) {
				String imgUrl = service.uploadFile(file);
				user.setProfileImageUrl(imgUrl);
			}
			return ResponseEntity.ok(service.save(user));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(service.getAll());
	}
	
	@PutMapping
	public ResponseEntity<User> update(@RequestBody User user) {
		return ResponseEntity.ok(service.update(user));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		return service.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
}
