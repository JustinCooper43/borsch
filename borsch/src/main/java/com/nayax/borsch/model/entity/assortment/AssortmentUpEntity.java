package com.nayax.borsch.model.entity.assortment;

import java.util.List;

public class AssortmentUpEntity {

    private Long dish;
    private List<Long> additionsId;
    private List<Long> remarksId;
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

    public List<Long> getAdditionsId() {
        return additionsId;
    }

    public void setAdditionsId(List<Long> additionsId) {
        this.additionsId = additionsId;
    }


    public List<Long> getRemarksId() {
        return remarksId;
    }

    public void setRemarksId(List<Long> remarksId) {
        this.remarksId = remarksId;
    }
}
