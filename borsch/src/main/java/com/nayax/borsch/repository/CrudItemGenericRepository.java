package com.nayax.borsch.repository;

import com.nayax.borsch.repository.impl.TablesType;

import java.util.List;
import java.util.Optional;

public interface CrudItemGenericRepository<T, U> {

    T add(T entity, U nameTable);

    T update(T entity, U nameTable);

    Optional<T> findById(Long id, U nameTable);

    List<T> findAll(U nameTable);

    T delete(Long id, U nameTable);

    List<T> findAllPage(int page, int pageSize, U nameTable);
}
