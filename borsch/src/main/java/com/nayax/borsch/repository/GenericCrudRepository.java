package com.nayax.borsch.repository;

import java.util.List;
import java.util.Optional;

public interface GenericCrudRepository <T,V> {

    T add(T entity);
    T update(T entity);
    Optional<T> findById(Long id);
    List<T> findAll();
    boolean delete(Long id);
    boolean delete(T entity);
    List< ? extends T> getAllByFilter(V filter);

}
