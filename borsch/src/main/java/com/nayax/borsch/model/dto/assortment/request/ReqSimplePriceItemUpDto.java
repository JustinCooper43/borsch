package com.nayax.borsch.model.dto.assortment.request;

import java.math.BigDecimal;

public class ReqSimplePriceItemUpDto {

    private Long itemId;
    private String name;
    private BigDecimal cost;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
