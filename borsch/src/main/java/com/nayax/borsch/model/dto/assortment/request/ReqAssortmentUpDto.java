package com.nayax.borsch.model.dto.assortment.request;

import java.util.List;

public class ReqAssortmentUpDto {

    private Long dish;
    private List<Long> additions;
    private List<Long> remarks;
    private boolean halfAble;

    public boolean isHalfAble() {
        return halfAble;
    }

    public void setHalfAble(boolean halfAble) {
        this.halfAble = halfAble;
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


    public List<Long> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<Long> remarks) {
        this.remarks = remarks;
    }
}
