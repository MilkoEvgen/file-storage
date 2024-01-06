package org.example.mapper;

import org.example.dto.EventDto;
import org.example.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventMapper {

    public static Event toEvent(EventDto eventDto){
        return Event.builder()
                .id(eventDto.getId())
                .user(UserMapper.toUser(eventDto.getUser()))
                .file(FileMapper.toFile(eventDto.getFile()))
                .build();
    }

    public static EventDto toEventDto(Event event){
        return EventDto.builder()
                .id(event.getId())
                .file(FileMapper.toFileDto(event.getFile()))
                .build();
    }

    public static List<EventDto> toEventDtoList(List<Event> events){
        if (events == null){
            return new ArrayList<>();
        }
        return events.stream()
                .map(EventMapper::toEventDto)
                .collect(Collectors.toList());
    }
}
