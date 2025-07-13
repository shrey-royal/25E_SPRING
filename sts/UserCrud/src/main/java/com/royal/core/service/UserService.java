package com.royal.core.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.royal.core.entity.User;

public interface UserService {
	User save(User user);
	List<User> getAll();
	Optional<User> findById(Long id);
	User update(User user);
	void deleteById(Long id);
	
	String uploadFile(MultipartFile file) throws IOException;
}
