package com.example.k3bootsecurity.service;

import java.util.List;

public interface AppService<T> {
    T getByName(String name);

    T getById(Long id);

    List<T> getAll();

    void saveOrUpdate(T object);

    void remove(T object);

}
