package org.example;

import org.example.dto.UserDto;
import org.example.exceptions.EntityNotFoundException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.impl.UserServiceImpl;
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
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void init(){
        user = User.builder()
                .id(1)
                .name("userName")
                .build();
        userDto = UserDto.builder()
                .name("userName")
                .build();
    }

    @Test
    public void createShouldReturnUser(){
        Mockito.when(userRepository.create(any())).thenReturn(user);
        UserDto actual = userService.create(userDto);
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("userName", actual.getName());
        Assertions.assertEquals(0, actual.getEvents().size());
        Mockito.verify(userRepository).create(any());
    }

    @Test
    public void getByIdShouldReturnUser(){
        Mockito.when(userRepository.getById(1)).thenReturn(user);
        UserDto actual = userService.getById(1);
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("userName", actual.getName());
        Assertions.assertEquals(0, actual.getEvents().size());
        Mockito.verify(userRepository).getById(1);
    }

    @Test
    public void getByIdShouldThrowIllegalArgumentExceptionIfIdIsNull(){
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.getById(null));
        Assertions.assertEquals("ID cannot be null", e.getMessage());
    }

    @Test
    public void getByIdShouldThrowEntityNotFoundExceptionIfUserNotExists(){
        Mockito.when(userRepository.getById(1)).thenReturn(null);
        EntityNotFoundException e = Assertions.assertThrows(EntityNotFoundException.class,
                () -> userService.getById(1));
        Assertions.assertEquals("User not exists", e.getMessage());
        Mockito.verify(userRepository).getById(1);
    }

    @Test
    public void getAllShouldReturnListOfUsers(){
        Mockito.when(userRepository.getAll()).thenReturn(List.of(user));
        List<UserDto> actual = userService.getAll();
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(1, actual.get(0).getId());
        Assertions.assertEquals("userName", actual.get(0).getName());
        Assertions.assertEquals(0, actual.get(0).getEvents().size());
        Mockito.verify(userRepository).getAll();
    }

    @Test
    public void getAllShouldReturnEmptyList(){
        Mockito.when(userRepository.getAll()).thenReturn(List.of());
        List<UserDto> actual = userService.getAll();
        Assertions.assertEquals(0, actual.size());
        Mockito.verify(userRepository).getAll();
    }

    @Test
    public void updateShouldReturnUser(){
        Mockito.when(userRepository.update(any())).thenReturn(user);
        userDto.setId(1);
        UserDto actual = userService.update(userDto);
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("userName", actual.getName());
        Assertions.assertEquals(0, actual.getEvents().size());
        Mockito.verify(userRepository).update(any());
    }

    @Test
    public void updateShouldThrowIllegalArgumentExceptionIfIdIsNull(){
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.update(userDto));
        Assertions.assertEquals("ID cannot be null", e.getMessage());
    }

    @Test
    public void deleteShouldReturnTrue(){
        Mockito.when(userRepository.delete(1)).thenReturn(true);
        Assertions.assertTrue(userService.delete(1));
        Mockito.verify(userRepository).delete(1);
    }

    @Test
    public void deleteShouldThrowIllegalArgumentExceptionIfIdIsNull(){
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.delete(null));
        Assertions.assertEquals("ID cannot be null", e.getMessage());
    }
}
