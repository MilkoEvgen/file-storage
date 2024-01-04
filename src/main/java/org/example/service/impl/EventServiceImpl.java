package org.example.service.impl;

import org.example.model.Event;
import org.example.repository.EventRepository;
import org.example.repository.hibernateImpl.EventRepositoryImpl;
import org.example.service.EventService;

public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository = new EventRepositoryImpl();


    @Override
    public Event create(Event event) {
        return eventRepository.create(event);
    }

    @Override
    public Event getById(Integer id) {
        return eventRepository.getById(id);
    }

    @Override
    public Event update(Event event) {
        return eventRepository.update(event);
    }

    @Override
    public boolean delete(Integer id) {
        return eventRepository.delete(id);
    }
}
