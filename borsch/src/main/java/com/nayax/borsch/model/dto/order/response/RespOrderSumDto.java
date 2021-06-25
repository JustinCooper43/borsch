package com.nayax.borsch.model.dto.order.response;

import com.nayax.borsch.model.dto.user.response.RespUserDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class RespOrderSumDto {

    private RespUserDto user;
    private List<RespOrderItemDto> orders;
    private Integer paymentType;
    private BigDecimal amount;
    private BigDecimal paidAmount;
    private LocalDate orderDate;


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

    public List<RespOrderItemDto> getOrders() {
        return orders;
    }

    public void setOrders(List<RespOrderItemDto> orders) {
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
