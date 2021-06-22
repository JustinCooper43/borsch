package com.nayax.borsch.repository;

import com.nayax.borsch.exceptions.NotUpdateException;

import java.util.List;
import java.util.Optional;

public interface GenericCrudRepository <T,V> {

    T add(T entity);
    T update(T entity) throws NotUpdateException;
    Optional<T> findById(Long id);
    List<T> findAll();
    boolean delete(Long id);
    boolean delete(T entity);
    List< T> getAllByFilter(V filter);

}
