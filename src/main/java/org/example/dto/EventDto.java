package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Integer id;
    @JsonIgnore
    private UserDto user;
    private FileDto file;
}
