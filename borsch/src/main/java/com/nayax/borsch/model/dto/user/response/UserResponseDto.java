package com.nayax.borsch.model.dto.user.response;

import com.nayax.borsch.model.dto.user.response.nested.RoleResponseDto;
import io.swagger.annotations.ApiModelProperty;

public class UserResponseDto {
    //TODO unmock values
    @ApiModelProperty(position = 1, value = "User Id")
    private Long id = 13L;
    @ApiModelProperty(position = 2, value = "User Email")
    private String eMail = "adress@server.net";
    @ApiModelProperty(position = 3, value = "User Name")
    private String name = "Fname";
    @ApiModelProperty(position = 4, value = "User Role")
    private RoleResponseDto role = new RoleResponseDto();
    @ApiModelProperty(position = 5, value = "User Phone Number")
    private String phoneNumber = "+380123456789";

    public RoleResponseDto getRole() {
        return role;
    }

    public void setRole(RoleResponseDto role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
