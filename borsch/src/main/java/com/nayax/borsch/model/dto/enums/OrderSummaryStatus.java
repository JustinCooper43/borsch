package com.nayax.borsch.model.dto.enums;

public enum OrderSummaryStatus {
    OPEN("open"),
    NOT_OPEN("notOpen");

    String status;

    OrderSummaryStatus(String status) {
        this.status = status;
    }
}
