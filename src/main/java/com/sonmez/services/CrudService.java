package com.sonmez.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CrudService <T> {
    T save(T entity);

    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    Optional<T> findOne(Long id);

    boolean isExists(Long id);

    void delete(Long id);
}
