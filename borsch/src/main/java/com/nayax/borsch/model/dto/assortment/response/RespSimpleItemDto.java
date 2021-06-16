package com.nayax.borsch.model.dto.assortment.response;

public class RespSimpleItemDto {
    //TODO unmock values
    private Long id = 42L;
    private String name = "SimpleItemName";

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
}
