package com.nayax.borsch.model.dto.request;

public class UserAddDto {
    private String eMail;
    private String name;

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
