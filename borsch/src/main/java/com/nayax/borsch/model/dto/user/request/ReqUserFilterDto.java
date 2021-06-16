package com.nayax.borsch.model.dto.user.request;

public class ReqUserFilterDto {
    private Long id;
    private String eMailPart;
    private String namePart;
    private Long roleId;
    private String phoneNumberPart;
    private String deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String geteMailPart() {
        return eMailPart;
    }

    public void seteMailPart(String eMailPart) {
        this.eMailPart = eMailPart;
    }

    public String getNamePart() {
        return namePart;
    }

    public void setNamePart(String namePart) {
        this.namePart = namePart;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getPhoneNumberPart() {
        return phoneNumberPart;
    }

    public void setPhoneNumberPart(String phoneNumberPart) {
        this.phoneNumberPart = phoneNumberPart;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
}
