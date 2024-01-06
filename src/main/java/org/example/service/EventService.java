package org.example.service;

import org.example.dto.EventDto;

import java.util.List;

public interface EventService {
    EventDto getById(Integer id);
    List<EventDto> getAll();
}
