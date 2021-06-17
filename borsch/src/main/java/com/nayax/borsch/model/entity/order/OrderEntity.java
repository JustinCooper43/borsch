package com.nayax.borsch.model.entity.order;

import java.time.LocalDateTime;

public class OrderEntity {

    private Long orderSummaryId;
    private LocalDateTime creationTime;
    private Long id;
    private Long Userid;
    private Long shawarmaTypeId;
    private Boolean cutInHalf;
    private Long remarkId;
    private  Long extraItemId;
    private Long orderItemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShawarmaTypeId() {
        return shawarmaTypeId;
    }

    public void setShawarmaTypeId(Long shawarmaTypeId) {
        this.shawarmaTypeId = shawarmaTypeId;
    }

    public Boolean getCutInHalf() {
        return cutInHalf;
    }

    public void setCutInHalf(Boolean cutInHalf) {
        this.cutInHalf = cutInHalf;
    }

    public Long getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(Long remarkId) {
        this.remarkId = remarkId;
    }

    public Long getExtraItemId() {
        return extraItemId;
    }

    public void setExtraItemId(Long extraItemId) {
        this.extraItemId = extraItemId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getOrderSummaryId() {
        return orderSummaryId;
    }

    public void setOrderSummaryId(Long orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Long getUserid() {
        return Userid;
    }

    public void setUserid(Long userid) {
        Userid = userid;
    }
}
