package com.royal.core.repository;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.royal.core.entity.UserEntity;

@Repository
public class UserRepository {
	private final Map<Integer, UserEntity> userDb = new HashMap<>();	//fake db
	private int currentId = 1;
	
	public List<UserEntity> findAll() {
		return new ArrayList<>(userDb.values());
	}
	
	public UserEntity findById(int id) {
		return userDb.get(id);
	}
	
	public void save(UserEntity user) {
		if (user.getId() == 0) {
			user.setId(currentId);
		}
		userDb.put(currentId++, user);
	}
	
	public void update(UserEntity user) {
		if (userDb.containsKey(user.getId())) {
			userDb.put(user.getId(), user);
		}
	}
	
	public void delete(int id) {
		userDb.remove(id);
	}
}
