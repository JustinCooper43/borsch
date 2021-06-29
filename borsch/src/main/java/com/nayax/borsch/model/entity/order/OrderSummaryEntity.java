package com.nayax.borsch.model.entity.order;

import com.nayax.borsch.model.entity.user.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OrderSummaryEntity {
    private UserEntity user;
    private List<OrderEntity> orders;
    private Integer paymentType;
    private BigDecimal totalOrdersCost;
    private BigDecimal payedSum;
    private LocalDate orderDate;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getTotalOrdersCost() {
        return totalOrdersCost;
    }

    public void setTotalOrdersCost(BigDecimal totalOrdersCost) {
        this.totalOrdersCost = totalOrdersCost;
    }

    public BigDecimal getPayedSum() {
        return payedSum;
    }

    public void setPayedSum(BigDecimal payedSum) {
        this.payedSum = payedSum;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
