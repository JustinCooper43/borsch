package com.nayax.borsch.example.model.response;

import com.nayax.borsch.model.dto.response.ErrorDto;

import java.util.ArrayList;
import java.util.List;

public class ResponseDto<T> {
    private T data;
    private List<ErrorDto> errors;

    public ResponseDto(T data) {
        this.data = data;
        errors = new ArrayList<>();
    }

    public ResponseDto(List<ErrorDto> errors) {
        this.errors = errors;
    }

    public ResponseDto() {
        errors = new ArrayList<>();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<ErrorDto> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDto> errors) {
        this.errors = errors;
    }
}