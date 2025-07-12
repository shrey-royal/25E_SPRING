package com.royal.core.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.royal.core.entity.User;
import com.royal.core.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService service;
	
	@PostMapping
	public ResponseEntity<User> save(@Valid @RequestBody User user) {
		return ResponseEntity.ok(service.save(user));
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(service.getAll());
	}
	
	@PutMapping
	public ResponseEntity<User> update(User user) {
		return service.findById(user.getId())
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
}
