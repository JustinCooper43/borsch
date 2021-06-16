package com.nayax.borsch.model.dto.assortment.request;

import java.math.BigDecimal;

public class ReqSimplePriceItemAddDto {

    private String name;
    private BigDecimal cost;


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
