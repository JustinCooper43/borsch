package com.nayax.borsch.model.dto.user.request;

public class ReqProfileAddDto {

    private ReqUserAddDto userAddDto;
    private ReqCashierAddDto cashierAddDto;

    public ReqUserAddDto getUserAddDto() {
        return userAddDto;
    }

    public void setUserAddDto(ReqUserAddDto userAddDto) {
        this.userAddDto = userAddDto;
    }

    public ReqCashierAddDto getCashierAddDto() {
        return cashierAddDto;
    }

    public void setCashierAddDto(ReqCashierAddDto cashierAddDto) {
        this.cashierAddDto = cashierAddDto;
    }
}
