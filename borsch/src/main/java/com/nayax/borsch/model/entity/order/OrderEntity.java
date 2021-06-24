package com.nayax.borsch.model.entity.order;

import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;

import java.time.LocalDateTime;
import java.util.List;

public class OrderEntity {

    private Long orderId;
    private ShawarmaItemEntity dish;
    private List<GeneralPriceItemEntity> additions;
    private GeneralPriceItemEntity drink;
    private GeneralPriceItemEntity remark;
    private boolean cut;
    private Integer quantity;
    private Long userId;
    private LocalDateTime creationTime;
    //TODO set current order in service?
    private Long orderSummaryId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public ShawarmaItemEntity getDish() {
        return dish;
    }

    public void setDish(ShawarmaItemEntity dish) {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
