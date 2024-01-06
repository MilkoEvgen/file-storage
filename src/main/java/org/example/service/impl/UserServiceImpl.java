package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserDto;
import org.example.exceptions.EntityNotFoundException;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto create(UserDto userDto) {
        log.info("in create, user - " + userDto);
        User user = userRepository.create(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto getById(Integer id) {
        log.info("in getById, id - " + id);
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        User user = userRepository.getById(id);
        if (user == null){
            throw new EntityNotFoundException("User not exists");
        }
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        log.info("in getAll");
        return UserMapper.toUserDtoList(userRepository.getAll());
    }

    @Override
    public UserDto update(UserDto userDto) {
        log.info("in update, user - " + userDto);
        if (userDto.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        User user = userRepository.update(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(user);
    }

    @Override
    public boolean delete(Integer id) {
        log.info("in delete, id - " + id);
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return userRepository.delete(id);
    }
}
