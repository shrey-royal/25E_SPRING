package com.royal.core.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.royal.core.entity.User;
import com.royal.core.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository repository;
	private final Cloudinary cloudinary;
	
	@Override
	public String uploadFile(MultipartFile file) throws IOException {
		Map<?, ?> uploadResultMap = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
		return uploadResultMap.get("secure_url").toString();
	}

	@Override
	public User save(User user) {
		return repository.save(user);
	}

	@Override
	public List<User> getAll() {
		return repository.findAll();
	}

	@Override
	public Optional<User> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public User update(User updatedUser) {
		return repository.findById(updatedUser.getId())
				.map(existing -> {
					existing.setName(updatedUser.getName());
					existing.setAge(updatedUser.getAge());
					existing.setEmail(updatedUser.getEmail());
					existing.setPassword(updatedUser.getPassword());
					return repository.save(existing);
				}).orElseThrow(() -> new RuntimeException("User id not found: " + updatedUser.getId()));
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}
