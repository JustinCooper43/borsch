package com.nayax.borsch.model.dto.assortment.response;

import java.util.List;

public class RespAssortmentDto {

    private RespSimpleItemDto dish;
    private List<RespSimpleItemDto> additions;
    private List<RespSimpleItemDto> remarks;
    private boolean halfAble;

    public RespSimpleItemDto getDish() {
        return dish;
    }

    public void setDish(RespSimpleItemDto dish) {
        this.dish = dish;
    }

    public List<RespSimpleItemDto> getAdditions() {
        return additions;
    }

    public void setAdditions(List<RespSimpleItemDto> additions) {
        this.additions = additions;
    }

    public List<RespSimpleItemDto> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<RespSimpleItemDto> remarks) {
        this.remarks = remarks;
    }

    public boolean isHalfAble() {
        return halfAble;
    }

    public void setHalfAble(boolean halfAble) {
        this.halfAble = halfAble;
    }
}
