package com.nayax.borsch.repository;

import java.util.List;
import java.util.Optional;

public interface CrudItemGenericRepository<T> {

    T add(T entity , String nameTable);
    T update(T entity, String nameTable);
    Optional<T> findById(Long id , String nameTable);
    List<T> findAll(String nameTable);
    boolean delete(Long id , String nameTable);
}
