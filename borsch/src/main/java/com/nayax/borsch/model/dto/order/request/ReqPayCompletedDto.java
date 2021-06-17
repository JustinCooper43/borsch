package com.nayax.borsch.model.dto.order.request;

import java.time.LocalDateTime;

public class ReqPayCompletedDto {

    private Long order;
    private Integer paymentType;

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }
}
