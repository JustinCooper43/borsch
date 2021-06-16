package com.nayax.borsch.model.dto.user.response;

public class RespProfileDto {
    //TODO unmock values
    private RespUserDto user = new RespUserDto();
    private RespCashierDto payments = new RespCashierDto();

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
