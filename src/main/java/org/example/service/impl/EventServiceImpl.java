package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EventDto;
import org.example.exceptions.EntityNotFoundException;
import org.example.mapper.EventMapper;
import org.example.model.Event;
import org.example.repository.EventRepository;
import org.example.service.EventService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public EventDto getById(Integer id) {
        log.info("in getById, id - " + id);
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Event event = eventRepository.getById(id);
        if (event == null){
            throw new EntityNotFoundException("Event not exists");
        }
        return EventMapper.toEventDto(event);
    }

    @Override
    public List<EventDto> getAll() {
        log.info("in getAll");
        return EventMapper.toEventDtoList(eventRepository.getAll());
    }
}
