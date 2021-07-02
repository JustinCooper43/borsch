package com.nayax.borsch.model.dto;

import java.util.List;

public class ResponseDto<T> {
    private T data;
    private List<ErrorDto> errors;
    private String status = "200";

    public ResponseDto() {
    }

    public String getStatus() {
        return status;
    }

    public ResponseDto<T> setStatus(String status) {
        this.status = status;
        return this;
    }

    public ResponseDto(T data) {
        this.data = data;
    }

    public ResponseDto(List<ErrorDto> errors) {
        this.errors = errors;
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
