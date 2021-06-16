package com.nayax.borsch.model.dto.order.request;

import java.time.LocalDateTime;

public class ReqPayCompletedDto {

    private Long userId;
    private LocalDateTime  orderDate;
    private Long paymentType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Long getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Long paymentType) {
        this.paymentType = paymentType;
    }
}
