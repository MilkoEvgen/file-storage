package org.example.service;

import org.example.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);
    UserDto getById(Integer id);
    List<UserDto> getAll();
    UserDto update(UserDto userDto);
    boolean delete(Integer id);
}
