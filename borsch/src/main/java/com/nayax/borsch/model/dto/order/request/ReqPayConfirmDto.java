package com.nayax.borsch.model.dto.order.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReqPayConfirmDto {

    private Long userId;
    private LocalDateTime orderDate;
    private BigDecimal paidNumber;

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

    public BigDecimal getPaidNumber() {
        return paidNumber;
    }

    public void setPaidNumber(BigDecimal paidNumber) {
        this.paidNumber = paidNumber;
    }
}
