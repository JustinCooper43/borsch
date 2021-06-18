package com.nayax.borsch.model.dto.assortment.request;

import java.util.List;

public class ReqAssortmentUpDto {

    private Long dish;
    private List<Long> additions;
    private boolean halfable;
    private List<Long> remarks;

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

    public boolean isHalfable() {
        return halfable;
    }

    public void setHalfable(boolean halfable) {
        this.halfable = halfable;
    }

    public List<Long> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<Long> remarks) {
        this.remarks = remarks;
    }
}
