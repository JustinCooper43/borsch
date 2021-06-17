package com.nayax.borsch.model.dto.user.request;

public class ReqProfileUpDto {

    private ReqUserUpdateDto user;
    private ReqCashierUpDto payments;

    public ReqUserUpdateDto getUser() {
        return user;
    }

    public void setUser(ReqUserUpdateDto user) {
        this.user = user;
    }

    public ReqCashierUpDto getPayments() {
        return payments;
    }

    public void setPayments(ReqCashierUpDto payments) {
        this.payments = payments;
    }
}
