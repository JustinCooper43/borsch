package com.nayax.borsch.model.entity.assortment;

import java.util.List;

public class AssortmentUpEntity {

    private Long dish;
    private List<Long> additionsId;
    private boolean isHalfAble;
    private List<Long> remarksId;

    public boolean isHalfAble() {
        return isHalfAble;
    }

    public void setHalfAble(boolean isHalfAble) {
        this.isHalfAble = isHalfAble;
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
