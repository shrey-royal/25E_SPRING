package com.bakery.service;

import com.bakery.dto.UserRequest;
import com.bakery.entity.User;
import java.util.List;

public interface UserService {
    User create(UserRequest dto);
    User getById(Long id);
    User getByUsername(String username);
    List<User> getAll();
    User update(Long id, UserRequest dto);
    void delete(Long id);
}
