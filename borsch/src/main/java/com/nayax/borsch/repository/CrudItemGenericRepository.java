package com.nayax.borsch.repository;

import com.nayax.borsch.model.entity.PageEntity;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.repository.impl.TablesType;

import java.util.List;
import java.util.Optional;

public interface CrudItemGenericRepository<T, U> {

    T add(T entity, U nameTable);

    T update(T entity, U nameTable);

    Optional<T> findById(Long id, U nameTable);

    List<T> findAll(U nameTable);

    Optional<T> delete(Long id, U nameTable);

    PageEntity<T> findAllPage(int page, int pageSize, U nameTable);
}
