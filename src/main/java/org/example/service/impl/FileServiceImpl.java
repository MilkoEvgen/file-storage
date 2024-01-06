package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.FileDto;
import org.example.exceptions.EntityNotFoundException;
import org.example.mapper.FileMapper;
import org.example.model.Event;
import org.example.model.File;
import org.example.model.User;
import org.example.repository.EventRepository;
import org.example.repository.FileRepository;
import org.example.repository.UserRepository;
import org.example.service.FileService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public FileDto createWithEvent(FileDto fileDto, Integer userId) {
        log.info("in createWithEvent, file - " + fileDto + ", user id = " + userId);
        if (userId == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        File file = fileRepository.create(FileMapper.toFile(fileDto));
        User user = userRepository.getById(userId);
        if (user == null){
            throw new EntityNotFoundException("User not exists");
        }
        Event event = Event.builder()
                .user(user)
                .file(file)
                .build();
        eventRepository.create(event);
        return FileMapper.toFileDto(file);
    }

    @Override
    public FileDto getById(Integer id) {
        log.info("in getById, id - " + id);
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        File file = fileRepository.getById(id);
        if (file == null){
            throw new EntityNotFoundException("File not exists");
        }
        return FileMapper.toFileDto(file);
    }

    @Override
    public List<FileDto> getAll() {
        log.info("in getAll");
        return FileMapper.toFileDtoList(fileRepository.getAll());
    }

    @Override
    public FileDto update(FileDto fileDto) {
        log.info("in update, file - " + fileDto);
        if (fileDto.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        File file = FileMapper.toFile(fileDto);
        return FileMapper.toFileDto(fileRepository.update(file));
    }

    @Override
    public boolean delete(Integer id) {
        log.info("in delete, id - " + id);
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return fileRepository.delete(id);
    }
}
