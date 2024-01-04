package org.example.service.impl;

import org.example.model.Event;
import org.example.model.File;
import org.example.model.User;
import org.example.repository.EventRepository;
import org.example.repository.FileRepository;
import org.example.repository.UserRepository;
import org.example.repository.hibernateImpl.EventRepositoryImpl;
import org.example.repository.hibernateImpl.FileRepositoryImpl;
import org.example.repository.hibernateImpl.UserRepositoryImpl;
import org.example.service.FileService;

public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository = new FileRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final EventRepository eventRepository = new EventRepositoryImpl();

    @Override
    public File create(File file) {
        return fileRepository.create(file);
    }

    @Override
    public File getById(Integer id) {
        return fileRepository.getById(id);
    }

    @Override
    public File update(File file) {
        return fileRepository.update(file);
    }

    @Override
    public boolean delete(Integer id) {
        return fileRepository.delete(id);
    }

    @Override
    public File createWithEvent(File file, Integer userId) {
        file = fileRepository.create(file);
        User user = userRepository.getById(userId);
        Event event = Event.builder()
                .user(user)
                .file(file)
                .build();
        eventRepository.create(event);
        return file;
    }
}
