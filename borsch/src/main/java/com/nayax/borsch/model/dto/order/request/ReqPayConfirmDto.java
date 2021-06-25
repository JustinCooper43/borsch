package com.nayax.borsch.model.dto.order.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReqPayConfirmDto {

    private Long userId;
    private LocalDate orderDate;
    private BigDecimal paid;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }
}
