package com.nayax.borsch.model.dto.assortment.request;

import java.util.List;

public class ReqAssortmentUpDto {

    private Long shawTypeId;
    private List<Long> additionsId;
    private Boolean isHalfAble;
    private List<Long> remarksId;

    public Long getShawTypeId() {
        return shawTypeId;
    }

    public void setShawTypeId(Long shawTypeId) {
        this.shawTypeId = shawTypeId;
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
