package com.nayax.borsch.model.dto.assortment.request;

import java.math.BigDecimal;

public class ReqSimplePriceItemUpDto {

    private Long id;
    private String name;
    private BigDecimal cost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
