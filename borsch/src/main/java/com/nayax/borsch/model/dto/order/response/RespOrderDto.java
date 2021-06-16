package com.nayax.borsch.model.dto.order.response;

import java.util.List;

public class RespOrderDto {
    //TODO unmock values
    private Long typeId = 1L;
    private List<Long> additionIdList = List.of(1L, 3L, 7L);
    private Long extraItemId = 12L;
    private Long remarkId = 4L;
    private Boolean cutInHalf = false;
    private Integer itemCount = 2;

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
