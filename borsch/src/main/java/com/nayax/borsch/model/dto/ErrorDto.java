package com.nayax.borsch.model.dto;

public class ErrorDto {
    private String message;
    private Integer code;

    public ErrorDto() {
    }

    public ErrorDto(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public ErrorDto(String message) {
        this.message = message;
    }

    public ErrorDto(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
