package com.nayax.borsch.model.dto.assortment.response;

import java.math.BigDecimal;

public class RespPriceItemDto {
    private Long id;
    private String name;
    private BigDecimal price;
    //TODO probably has to be of enum type
    private int type;

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
