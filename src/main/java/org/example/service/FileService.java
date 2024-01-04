package org.example.service;

import org.example.model.File;
import org.example.model.User;

import java.util.List;

public interface FileService {
    File create(File file);
    File getById(Integer id);
    File update(File file);
    boolean delete(Integer id);
    File createWithEvent(File file, Integer userId);
}
