package com.nayax.borsch.model.dto.user.request;

import com.nayax.borsch.model.dto.user.request.ReqCashierUpDto;
import com.nayax.borsch.model.dto.user.request.ReqUserUpdateDto;

public class ReqProfileUpDto {

    private ReqUserUpdateDto userUpdateDto;
    private ReqCashierUpDto cashierUpDto;

    public ReqUserUpdateDto getUserUpdateDto() {
        return userUpdateDto;
    }

    public void setUserUpdateDto(ReqUserUpdateDto userUpdateDto) {
        this.userUpdateDto = userUpdateDto;
    }

    public ReqCashierUpDto getCashierUpDto() {
        return cashierUpDto;
    }

    public void setCashierUpDto(ReqCashierUpDto cashierUpDto) {
        this.cashierUpDto = cashierUpDto;
    }
}
