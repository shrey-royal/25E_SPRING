package com.royal.core.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royal.core.entity.UserEntity;
import com.royal.core.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}
	
	public void saveUser(UserEntity entity) {
		userRepository.save(entity);
	}
	
	public UserEntity getUserById(int id) {
		return userRepository.findById(id);
	}
	
	public void updateUser(UserEntity entity) {
		userRepository.update(entity);
	}
	
	public void deleteUser(int id) {
		userRepository.delete(id);
	}
	
	
}
