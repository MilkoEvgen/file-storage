package org.example.mapper;

import org.example.dto.FileDto;
import org.example.model.File;

import java.util.List;
import java.util.stream.Collectors;

public class FileMapper {

    public static File toFile(FileDto fileDto){
        return File.builder()
                .id(fileDto.getId())
                .name(fileDto.getName())
                .filePath(fileDto.getFilePath())
                .build();
    }

    public static FileDto toFileDto(File file){
        return FileDto.builder()
                .id(file.getId())
                .name(file.getName())
                .filePath(file.getFilePath())
                .build();
    }

    public static List<FileDto> toFileDtoList(List<File> files){
        return files.stream()
                .map(FileMapper::toFileDto)
                .collect(Collectors.toList());
    }
}
