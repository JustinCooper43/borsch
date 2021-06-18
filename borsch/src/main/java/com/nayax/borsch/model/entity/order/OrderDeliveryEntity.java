package com.nayax.borsch.model.entity.order;

import java.time.LocalDateTime;

public class OrderDeliveryEntity {
    private OrderEntity order;
    private Integer quantity;
    private LocalDateTime orderDate;

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
