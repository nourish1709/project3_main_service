package com.nourish1709.project3_main_service.services;

import java.util.List;

public interface CrudInterface<T> {
    T create(T t);

    List<T> getAll();

    T getById(Long id);

    T update(Long id, T t);

    void delete(Long id);
}
