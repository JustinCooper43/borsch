package com.nayax.borsch.utility.enums;

public enum ErrorStatus {
    UNAUTHORIZED("401"),
    FORBIDDEN("403"),
    NOT_FOUND("404"),
    UNPROCESSIBLE("422"),
    SERVER_ERROR("500"),
    OK("200");

    public String statusName;

    ErrorStatus(String statusName) {
        this.statusName = statusName;
    }
}
