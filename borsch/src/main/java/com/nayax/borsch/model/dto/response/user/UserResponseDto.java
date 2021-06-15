package com.nayax.borsch.model.dto.response.user;

import io.swagger.annotations.ApiModelProperty;

public class UserResponseDto {
    @ApiModelProperty(position = 1, value = "User Id")
    private Long id;
    @ApiModelProperty(position = 2, value = "User Email")
    private String eMail;
    @ApiModelProperty(position = 3, value = "User Name")
    private String name;
    @ApiModelProperty(position = 4, value = "User Role")
    private String roleName;
    @ApiModelProperty(position = 5, value = "User Phone Number")
    private String phoneNumber;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
