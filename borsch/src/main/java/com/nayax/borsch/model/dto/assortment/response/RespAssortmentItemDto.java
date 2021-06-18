package com.nayax.borsch.model.dto.assortment.response;

import java.math.BigDecimal;

public class RespAssortmentItemDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private boolean halfAble;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isHalfAble() {
        return halfAble;
    }

    public void setHalfAble(boolean halfAble) {
        this.halfAble = halfAble;
    }
}
