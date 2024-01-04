package org.example.service.impl;

import org.example.model.Event;
import org.example.model.User;
import org.example.model.UserDto;
import org.example.repository.EventRepository;
import org.example.repository.UserRepository;
import org.example.repository.hibernateImpl.EventRepositoryImpl;
import org.example.repository.hibernateImpl.UserRepositoryImpl;
import org.example.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final EventRepository eventRepository = new EventRepositoryImpl();

    @Override
    public UserDto create(User user) {
        User newUser = userRepository.create(user);
        return UserDto.builder()
                .id(newUser.getId())
                .name(newUser.getName())
                .build();
    }

    @Override
    public UserDto getById(Integer id) {
        List<Event> events = eventRepository.getEventsByUserId(id);
        User newUser = userRepository.getById(id);
        return UserDto.builder()
                .id(newUser.getId())
                .name(newUser.getName())
                .events(events)
                .build();
    }

    @Override
    public UserDto update(User user) {
        List<Event> events = eventRepository.getEventsByUserId(user.getId());
        User newUser = userRepository.update(user);
        return UserDto.builder()
                .id(newUser.getId())
                .name(newUser.getName())
                .events(events)
                .build();
    }

    @Override
    public boolean delete(Integer id) {
        return userRepository.delete(id);
    }
}
