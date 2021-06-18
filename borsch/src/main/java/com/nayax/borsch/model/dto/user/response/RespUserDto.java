package com.nayax.borsch.model.dto.user.response;

import com.nayax.borsch.model.dto.user.response.nested.RoleDto;
import io.swagger.annotations.ApiModelProperty;

public class RespUserDto {
    @ApiModelProperty(position = 1, value = "User Id")
    private Long id;
    @ApiModelProperty(position = 2, value = "User Email")
    private String eMail;
    @ApiModelProperty(position = 3, value = "User FirstName")
    private String firstName;
    @ApiModelProperty(position = 4, value = "User LastName")
    private String lastName;
    @ApiModelProperty(position = 5, value = "User Role")
    private RoleDto role;
    @ApiModelProperty(position = 6, value = "User Phone Number")
    private String phone;

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phoneNumber) {
        this.phone = phoneNumber;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
