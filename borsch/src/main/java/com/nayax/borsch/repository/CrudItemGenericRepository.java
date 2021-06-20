package com.nayax.borsch.repository;

import com.nayax.borsch.repository.impl.TablesType;

import java.util.List;
import java.util.Optional;

public interface CrudItemGenericRepository<T> {

    T add(T entity , TablesType nameTable);

    T update(T entity, TablesType nameTable);
    Optional<T> findById(Long id , TablesType nameTable);
    List<T> findAll(TablesType nameTable);
    T delete(Long id , TablesType nameTable);
}
