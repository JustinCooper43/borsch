package com.nayax.borsch.model.dto.order.request;

import java.time.LocalDateTime;

public class ReqPayCompletedDto {

    private Long orderId;
    private Long paymentType;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Long paymentType) {
        this.paymentType = paymentType;
    }

}
