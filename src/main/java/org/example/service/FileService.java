package org.example.service;

import org.example.dto.FileDto;

import java.util.List;

public interface FileService {
    FileDto createWithEvent(FileDto fileDto, Integer userId);
    FileDto getById(Integer id);
    List<FileDto> getAll();
    FileDto update(FileDto fileDto);
    boolean delete(Integer id);
}
