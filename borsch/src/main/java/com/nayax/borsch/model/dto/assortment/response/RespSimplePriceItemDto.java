package com.nayax.borsch.model.dto.assortment.response;

import java.math.BigDecimal;

public class RespSimplePriceItemDto {
    //TODO unmock values
    private Long id = 23L;
    private String name = "SimplePriceItemName";
    private BigDecimal price = new BigDecimal("133.33");

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
}
