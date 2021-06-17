package com.nayax.borsch.model.dto.user.request;

public class ReqProfileAddDto {

    private ReqUserAddDto user;
    private ReqCashierAddDto payments;

    public ReqUserAddDto getUser() {
        return user;
    }

    public void setUser(ReqUserAddDto user) {
        this.user = user;
    }

    public ReqCashierAddDto getPayments() {
        return payments;
    }

    public void setPayments(ReqCashierAddDto payments) {
        this.payments = payments;
    }
}
