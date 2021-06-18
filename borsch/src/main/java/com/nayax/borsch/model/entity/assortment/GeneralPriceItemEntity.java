package com.nayax.borsch.model.entity.assortment;

import java.math.BigDecimal;

public class GeneralPriceItemEntity {

    private Long id;
    private String name;
    private BigDecimal price;
    private String active;

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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
