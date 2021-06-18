package com.nayax.borsch.model.dto.order.response;

import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;

import java.time.LocalDateTime;
import java.util.List;

public class RespOrderItemDto {
    //TODO add cost
    private RespSimplePriceItemDto dish;
    private List<RespSimplePriceItemDto> additions;
    private RespSimplePriceItemDto drink;
    private RespSimpleItemDto remark;
    private boolean cut;
    private Integer quantity;
    private LocalDateTime orderDate;

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public RespSimplePriceItemDto getDish() {
        return dish;
    }

    public void setDish(RespSimplePriceItemDto dish) {
        this.dish = dish;
    }

    public List<RespSimplePriceItemDto> getAdditions() {
        return additions;
    }

    public void setAdditions(List<RespSimplePriceItemDto> additions) {
        this.additions = additions;
    }

    public RespSimplePriceItemDto getDrink() {
        return drink;
    }

    public void setDrink(RespSimplePriceItemDto drink) {
        this.drink = drink;
    }

    public RespSimpleItemDto getRemark() {
        return remark;
    }

    public void setRemark(RespSimpleItemDto remark) {
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
