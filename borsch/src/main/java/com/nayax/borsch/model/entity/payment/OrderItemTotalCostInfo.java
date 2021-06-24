package com.nayax.borsch.model.entity.payment;

import java.math.BigDecimal;

public class OrderItemTotalCostInfo {

    private Long orderId;
    private Long userId;
    private BigDecimal drinkCost;
    private BigDecimal dishCost;
    private BigDecimal additionCost;
    private Integer quantity;
    private BigDecimal payedSum;
    private BigDecimal totalCost;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getDrinkCost() {
        return drinkCost;
    }

    public void setDrinkCost(BigDecimal drinkCost) {
        this.drinkCost = drinkCost;
    }

    public BigDecimal getDishCost() {
        return dishCost;
    }

    public void setDishCost(BigDecimal dishCost) {
        this.dishCost = dishCost;
    }

    public BigDecimal getAdditionCost() {
        return additionCost;
    }

    public void setAdditionCost(BigDecimal additionCost) {
        this.additionCost = additionCost;
    }

    public BigDecimal getPayedSum() {
        return payedSum;
    }

    public void setPayedSum(BigDecimal payedSum) {
        this.payedSum = payedSum;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
