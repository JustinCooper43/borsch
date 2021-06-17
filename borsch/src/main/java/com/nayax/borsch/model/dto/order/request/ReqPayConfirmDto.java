package com.nayax.borsch.model.dto.order.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReqPayConfirmDto {

    private Long user;
    private LocalDateTime orderDate;
    private BigDecimal paid;



    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }
}
