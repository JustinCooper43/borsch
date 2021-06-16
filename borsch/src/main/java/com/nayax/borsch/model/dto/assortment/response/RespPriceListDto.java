package com.nayax.borsch.model.dto.assortment.response;

import java.util.List;

public class RespPriceListDto {

    List <RespPriceItemDto> list;

    public List<RespPriceItemDto> getList() {
        return list;
    }

    public void setList(List<RespPriceItemDto> list) {
        this.list = list;
    }
}
