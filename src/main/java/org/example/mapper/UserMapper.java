package org.example.mapper;

import org.example.dto.UserDto;
import org.example.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toUser(UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .build();
    }

    public static UserDto toUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .events(EventMapper.toEventDtoList(user.getEvents()))
                .build();
    }

    public static List<UserDto> toUserDtoList(List<User> users){
        return users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }
}
