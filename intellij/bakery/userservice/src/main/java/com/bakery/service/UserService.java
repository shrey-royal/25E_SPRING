package com.bakery.service;

import com.bakery.entity.User;
import com.bakery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;

    public User createUser(User user) {
        return repo.save(user);
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public User findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public User updateUser(User user) {
        return repo.findById(user.getId())
                .map(existing -> {
                    existing.setName(user.getName());
                    existing.setEmail(user.getName());
                    existing.setPassword(user.getPassword());
                    return repo.save(existing);
                }).orElseThrow(() -> new RuntimeException("User with id" + user.getId() + "not found"));
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
