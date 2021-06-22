package com.nayax.borsch.model.entity.assortment;

import java.math.BigDecimal;

public class ShawarmaItemEntity {
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

    public void setHalfAble(boolean halfable) {
        halfAble = halfable;
    }

    @Override
    public String toString() {
        return "ShawarmaItemEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", isHalfAble=" + halfAble +
                '}';
    }
}
