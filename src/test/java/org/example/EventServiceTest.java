package org.example;

import org.example.dto.EventDto;
import org.example.exceptions.EntityNotFoundException;
import org.example.model.Event;
import org.example.model.File;
import org.example.model.User;
import org.example.repository.EventRepository;
import org.example.service.impl.EventServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;
    @InjectMocks
    private EventServiceImpl eventService;
    private User user;
    private File file;
    private Event event;

    @BeforeEach
    void init(){
        user = User.builder()
                .id(1)
                .name("userName")
                .build();
        file = File.builder()
                .id(1)
                .name("fileName")
                .filePath("filePath")
                .build();
        event = Event.builder()
                .id(1)
                .user(user)
                .file(file)
                .build();
    }

    @Test
    public void getByIdShouldReturnEventDto(){
        Mockito.when(eventRepository.getById(1)).thenReturn(event);
        EventDto eventDto = eventService.getById(1);
        Assertions.assertEquals(1, eventDto.getId());
        Assertions.assertEquals(1, eventDto.getFile().getId());
        Assertions.assertEquals("fileName", eventDto.getFile().getName());
        Assertions.assertEquals("filePath", eventDto.getFile().getFilePath());
        Mockito.verify(eventRepository).getById(1);
    }

    @Test
    public void getByIdShouldThrowIllegalArgumentExceptionIfIdIsNull(){
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> eventService.getById(null));
        Assertions.assertEquals("ID cannot be null", e.getMessage());
    }

    @Test
    public void getByIdShouldThrowEntityNotFoundExceptionIfEventNotExists(){
        Mockito.when(eventRepository.getById(1)).thenReturn(null);
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class,
                () -> eventService.getById(1));
        Assertions.assertEquals("Event not exists", e.getMessage());
    }

    @Test
    public void getAllShouldReturnEventDtoList(){
        Mockito.when(eventRepository.getAll()).thenReturn(List.of(event));
        List <EventDto> eventDtos = eventService.getAll();
        Assertions.assertEquals(1, eventDtos.size());
        Assertions.assertEquals(1, eventDtos.get(0).getId());
        Assertions.assertEquals(1, eventDtos.get(0).getFile().getId());
        Assertions.assertEquals("fileName", eventDtos.get(0).getFile().getName());
        Assertions.assertEquals("filePath", eventDtos.get(0).getFile().getFilePath());
        Mockito.verify(eventRepository).getAll();
    }

    @Test
    public void getAllShouldReturnEmptyList(){
        Mockito.when(eventRepository.getAll()).thenReturn(List.of());
        List <EventDto> eventDtos = eventService.getAll();
        Assertions.assertEquals(0, eventDtos.size());
        Mockito.verify(eventRepository).getAll();
    }

}
