package com.nayax.borsch.model.dto.order.response;

import java.time.LocalDateTime;

public class RespOrderDeliveryDto {

    private RespOrderItemDto order;
    private Integer quantity;
    private LocalDateTime orderDate;

    public RespOrderItemDto getOrder() {
        return order;
    }

    public void setOrder(RespOrderItemDto order) {
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
