package com.nayax.borsch.model.dto.assortment.response;

import java.math.BigDecimal;

public class RespAssortmentItemDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private Boolean isHalfAble;

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

    public Boolean getHalfAble() {
        return isHalfAble;
    }

    public void setHalfAble(Boolean halfable) {
        isHalfAble = halfable;
    }
}
