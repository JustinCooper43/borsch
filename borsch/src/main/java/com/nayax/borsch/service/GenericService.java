package com.nayax.borsch.service;

import com.nayax.borsch.model.dto.response.ResponseDto;

import java.util.List;

public interface GenericService <T, U, V, R>{

    ResponseDto<R> add(T dto);
    ResponseDto<R> update(U dto);
    ResponseDto<R> get(Long id);
    ResponseDto<R> delete(Long id);
    ResponseDto<List<R>> getAll();
    ResponseDto<List<R>> getAllByFilter(V filter);


}
