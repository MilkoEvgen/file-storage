package org.example;

import org.example.dto.FileDto;
import org.example.exceptions.EntityNotFoundException;
import org.example.model.Event;
import org.example.model.File;
import org.example.model.User;
import org.example.repository.EventRepository;
import org.example.repository.FileRepository;
import org.example.repository.UserRepository;
import org.example.service.impl.FileServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @Mock
    private FileRepository fileRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EventRepository eventRepository;
    @InjectMocks
    private FileServiceImpl fileService;

    private FileDto fileDto;
    private File file;
    private User user;
    private Event event;

    @BeforeEach
    void init(){
        fileDto = FileDto.builder()
                .name("fileName")
                .filePath("filePath")
                .build();
        file = File.builder()
                .id(1)
                .name("fileName")
                .filePath("filePath")
                .build();
        user = User.builder()
                .id(1)
                .name("userName")
                .build();
        event = Event.builder()
                .id(1)
                .file(file)
                .build();
    }

    @Test
    public void createWithEventShouldReturnFile(){
        Mockito.when(fileRepository.create(any())).thenReturn(file);
        Mockito.when(userRepository.getById(1)).thenReturn(user);
        Mockito.when(eventRepository.create(any())).thenReturn(event);
        FileDto actual = fileService.createWithEvent(fileDto, 1);
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("fileName", actual.getName());
        Assertions.assertEquals("filePath", actual.getFilePath());
        Mockito.verify(fileRepository).create(any());
        Mockito.verify(userRepository).getById(1);
        Mockito.verify(eventRepository).create(any());
    }

    @Test
    public void createWithEventShouldThrowIllegalArgumentExceptionIfUserIdNull(){
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> fileService.createWithEvent(fileDto, null));
        Assertions.assertEquals("ID cannot be null", e.getMessage());
    }

    @Test
    public void createWithEventShouldThrowEntityNotFoundExceptionIfUserNotExists(){
        Mockito.when(fileRepository.create(any())).thenReturn(file);
        Mockito.when(userRepository.getById(1)).thenReturn(null);
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class,
                () -> fileService.createWithEvent(fileDto, 1));
        Assertions.assertEquals("User not exists", e.getMessage());
        Mockito.verify(fileRepository).create(any());
        Mockito.verify(userRepository).getById(1);
    }

    @Test
    public void getByIdShouldReturnFile(){
        Mockito.when(fileRepository.getById(1)).thenReturn(file);
        FileDto actual = fileService.getById(1);
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("fileName", actual.getName());
        Assertions.assertEquals("filePath", actual.getFilePath());
        Mockito.verify(fileRepository).getById(1);
    }

    @Test
    public void getByIdShouldThrowIllegalArgumentExceptionIfIdNull(){
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> fileService.getById(null));
        Assertions.assertEquals("ID cannot be null", e.getMessage());
    }

    @Test
    public void getByIdShouldThrowEntityNotFoundExceptionIfFileNotExists(){
        Mockito.when(fileRepository.getById(1)).thenReturn(null);
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class,
                () -> fileService.getById(1));
        Assertions.assertEquals("File not exists", e.getMessage());
        Mockito.verify(fileRepository).getById(1);
    }

    @Test
    public void getAllShouldReturnListOfFiles(){
        Mockito.when(fileRepository.getAll()).thenReturn(List.of(file));
        List<FileDto> actual = fileService.getAll();
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(1, actual.get(0).getId());
        Assertions.assertEquals("fileName", actual.get(0).getName());
        Assertions.assertEquals("filePath", actual.get(0).getFilePath());
        Mockito.verify(fileRepository).getAll();
    }

    @Test
    public void getAllShouldReturnEmptyList(){
        Mockito.when(fileRepository.getAll()).thenReturn(List.of());
        List<FileDto> actual = fileService.getAll();
        Assertions.assertEquals(0, actual.size());
        Mockito.verify(fileRepository).getAll();
    }

    @Test
    public void updateShouldReturnFile(){
        Mockito.when(fileRepository.update(any())).thenReturn(file);
        fileDto.setId(1);
        FileDto actual = fileService.update(fileDto);
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("fileName", actual.getName());
        Assertions.assertEquals("filePath", actual.getFilePath());
        Mockito.verify(fileRepository).update(any());
    }

    @Test
    public void updateShouldThrowIllegalArgumentExceptionIfIdNull(){
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> fileService.update(fileDto));
        Assertions.assertEquals("ID cannot be null", e.getMessage());
    }

    @Test
    public void deleteShouldReturnTrue(){
        Mockito.when(fileRepository.delete(1)).thenReturn(true);
        Assertions.assertTrue(fileService.delete(1));
        Mockito.verify(fileRepository).delete(1);
    }

    @Test
    public void deleteShouldThrowIllegalArgumentExceptionIfIdNull(){
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> fileService.delete(null));
        Assertions.assertEquals("ID cannot be null", e.getMessage());
    }
}
