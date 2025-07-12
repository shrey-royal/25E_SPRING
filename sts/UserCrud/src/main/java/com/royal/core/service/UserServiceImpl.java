package com.royal.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.royal.core.entity.User;
import com.royal.core.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository repository;

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
