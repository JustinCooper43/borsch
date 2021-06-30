package com.nayax.borsch.model.entity.order;

import java.time.LocalDate;

public class OrderDeliveryEntity {
    private OrderEntity order;
    private Integer quantity;
    private LocalDate orderDate;

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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
