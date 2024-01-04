package org.example.repository;

import java.util.List;

public interface GenericRepository <T,ID>{
    T create(T t);
    T getById(ID id);
    T update(T t);
    boolean delete(ID id);
}