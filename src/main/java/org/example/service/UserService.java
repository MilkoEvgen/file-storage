package org.example.service;

import org.example.model.User;
import org.example.model.UserDto;

public interface UserService {
    UserDto create(User user);
    UserDto getById(Integer id);
    UserDto update(User user);
    boolean delete(Integer id);
}
