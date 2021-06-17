package com.nayax.borsch.model.dto.assortment.request;

public class ReqSimpleItemUpDto {

    private Long id;
    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
