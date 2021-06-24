package com.nayax.borsch.model.entity.assortment;

import java.math.BigDecimal;
import java.util.Objects;

public class ShawarmaItemEntity {
    private Long id;
    private String name;
    private BigDecimal price;
    private boolean halfAble;
    private String active = "Y";
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShawarmaItemEntity entity = (ShawarmaItemEntity) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
