package org.example.repository;

import org.example.model.Event;

import java.util.List;

public interface EventRepository extends GenericRepository<Event, Integer> {
    List<Event> getEventsByUserId(Integer id);
}
