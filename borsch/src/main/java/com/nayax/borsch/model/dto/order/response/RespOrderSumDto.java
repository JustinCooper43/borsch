package com.nayax.borsch.model.dto.order.response;

import com.nayax.borsch.model.dto.user.response.RespUserDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public class RespOrderSumDto {

    private RespUserDto user;
    private List<RespOrderDto> orders;
    private Integer paymentType;
    private BigDecimal amount;
    private BigDecimal paidAmount;
    private LocalDateTime orderDate;


    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public RespUserDto getUser() {
        return user;
    }

    public void setUser(RespUserDto user) {
        this.user = user;
    }

    public List<RespOrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<RespOrderDto> orders) {
        this.orders = orders;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
