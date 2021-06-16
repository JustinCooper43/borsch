package com.nayax.borsch.model.dto.order.response;

import java.util.List;

public class RespOrderDto {
    private Long typeId;
    private List<Long> additionIdList;
    private Long extraItemId;
    private Long remarkId;
    private Boolean cutInHalf;
    private Integer itemCount;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public List<Long> getAdditionIdList() {
        return additionIdList;
    }

    public void setAdditionIdList(List<Long> additionIdList) {
        this.additionIdList = additionIdList;
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

    public Boolean getCutInHalf() {
        return cutInHalf;
    }

    public void setCutInHalf(Boolean cutInHalf) {
        this.cutInHalf = cutInHalf;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }
}
