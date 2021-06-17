package com.nayax.borsch.model.dto.user.response;

public class RespProfileDto {

    private RespUserDto user ;
    private RespCashierDto payments ;

    public RespUserDto getUser() {
        return user;
    }

    public void setUser(RespUserDto user) {
        this.user = user;
    }

    public RespCashierDto getPayments() {
        return payments;
    }

    public void setPayments(RespCashierDto payments) {
        this.payments = payments;
    }
}
