package com.nayax.borsch.model.dto.assortment.response;

import java.math.BigDecimal;

public class AssortmentItemDto {
    //TODO unmock values
    private Long id = 33L;
    private String name = "AssortmentItemName";
    private BigDecimal price = new BigDecimal("233.33");
    private Boolean isHalfable = false;

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

    public Boolean getHalfable() {
        return isHalfable;
    }

    public void setHalfable(Boolean halfable) {
        isHalfable = halfable;
    }
}
