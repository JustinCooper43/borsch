package com.nayax.borsch.model.dto;

public class ErrorDto {
    private String cause;
    private Integer code;

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
