package com.nayax.borsch.model.entity.assortment;


import java.util.List;

public class AssortmentRespEntity {

    private GeneralPriceItemEntity dish;
    private List<GeneralPriceItemEntity> additions;
    private List<GeneralPriceItemEntity> remarks;
    private boolean halfAble;

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

    public List<GeneralPriceItemEntity> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<GeneralPriceItemEntity> remarks) {
        this.remarks = remarks;
    }

    public boolean isHalfAble() {
        return halfAble;
    }

    public void setHalfAble(boolean halfAble) {
        this.halfAble = halfAble;
    }
}
