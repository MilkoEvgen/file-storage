package org.example.service;

import org.example.model.Event;
import org.example.model.File;

public interface EventService {
    Event create(Event event);
    Event getById(Integer id);
    Event update(Event event);
    boolean delete(Integer id);
}
