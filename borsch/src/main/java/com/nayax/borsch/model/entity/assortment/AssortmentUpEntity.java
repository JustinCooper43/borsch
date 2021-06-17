package com.nayax.borsch.model.entity.assortment;

import java.util.List;

public class AssortmentUpEntity {

    private Long dish;
    private List<Long> additionsId;
    private Boolean isHalfAble;
    private List<Long> remarksId;

    public Long getDish() {
        return dish;
    }

    public void setDish(Long dish) {
        this.dish = dish;
    }

    public List<Long> getAdditionsId() {
        return additionsId;
    }

    public void setAdditionsId(List<Long> additionsId) {
        this.additionsId = additionsId;
    }

    public Boolean getHalfAble() {
        return isHalfAble;
    }

    public void setHalfAble(Boolean halfAble) {
        isHalfAble = halfAble;
    }

    public List<Long> getRemarksId() {
        return remarksId;
    }

    public void setRemarksId(List<Long> remarksId) {
        this.remarksId = remarksId;
    }
}
