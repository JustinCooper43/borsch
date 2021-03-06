package com.nayax.borsch.model.dto.order.request;

import java.time.LocalDateTime;
import java.util.List;

public class ReqOrderItemAddDto {
    private Long userId;
    private Long dish;
    private List<Long> additions;
    private Long drink;
    private Long remark;
    private boolean cut;
    private Integer quantity;
    private LocalDateTime orderDate;

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

    public Long getDish() {
        return dish;
    }

    public void setDish(Long dish) {
        this.dish = dish;
    }

    public List<Long> getAdditions() {
        return additions;
    }

    public void setAdditions(List<Long> additions) {
        this.additions = additions;
    }

    public Long getDrink() {
        return drink;
    }

    public void setDrink(Long drink) {
        this.drink = drink;
    }

    public Long getRemark() {
        return remark;
    }

    public void setRemark(Long remark) {
        this.remark = remark;
    }

    public boolean isCut() {
        return cut;
    }

    public void setCut(boolean cut) {
        this.cut = cut;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
