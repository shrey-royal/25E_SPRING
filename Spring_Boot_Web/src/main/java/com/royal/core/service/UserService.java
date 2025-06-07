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
}
