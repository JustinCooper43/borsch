package com.nayax.borsch.model.dto.user.response.nested;

public class RoleResponseDto {
    //TODO unmock values
    private Long id = 2L;
    private String name = "Cashier";

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
