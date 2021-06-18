package com.nayax.borsch.model.entity.order;

import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;

import java.time.LocalDateTime;
import java.util.List;

public class OrderEntity {
    private Long orderId;
    private GeneralPriceItemEntity dish;
    private List<GeneralPriceItemEntity> additions;
    private GeneralPriceItemEntity drink;
    private GeneralPriceItemEntity remark;
    private boolean cut;
    private Integer quantity;
    private Long Userid;
    private LocalDateTime creationTime;
    private Long orderSummaryId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public GeneralPriceItemEntity getDish() {
        return dish;
    }

    public void setDish(GeneralPriceItemEntity dish) {
        this.dish = dish;
    }

    public List<GeneralPriceItemEntity> getAdditions() {
        return additions;
    }

    public void setAdditions(List<GeneralPriceItemEntity> additions) {
        this.additions = additions;
    }

    public GeneralPriceItemEntity getDrink() {
        return drink;
    }

    public void setDrink(GeneralPriceItemEntity drink) {
        this.drink = drink;
    }

    public GeneralPriceItemEntity getRemark() {
        return remark;
    }

    public void setRemark(GeneralPriceItemEntity remark) {
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

    public Long getUserid() {
        return Userid;
    }

    public void setUserid(Long userid) {
        Userid = userid;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Long getOrderSummaryId() {
        return orderSummaryId;
    }

    public void setOrderSummaryId(Long orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
    }
}
