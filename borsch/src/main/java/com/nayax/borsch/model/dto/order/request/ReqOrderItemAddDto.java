package com.nayax.borsch.model.dto.order.request;

import java.util.List;

public class ReqOrderItemAddDto {

    private Long id;
    private List<Long> additionsId;
    private Long extraItemId;
    private Long remarkId;
    private Boolean isHalfAble;
    private Long quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getAdditionsId() {
        return additionsId;
    }

    public void setAdditionsId(List<Long> additionsId) {
        this.additionsId = additionsId;
    }

    public Long getExtraItemId() {
        return extraItemId;
    }

    public void setExtraItemId(Long extraItemId) {
        this.extraItemId = extraItemId;
    }

    public Long getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(Long remarkId) {
        this.remarkId = remarkId;
    }

    public Boolean getHalfAble() {
        return isHalfAble;
    }

    public void setHalfAble(Boolean halfAble) {
        isHalfAble = halfAble;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
