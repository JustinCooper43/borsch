package com.nayax.borsch.model.dto.assortment.response;

import java.math.BigDecimal;

public class PriceItemDto {
    //TODO unmock values
    private Long id = 29L;
    private String name = "PriceItemName";
    private BigDecimal price = new BigDecimal("166.66");
    //TODO probably has to be of enum type
    private int type = 1;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
