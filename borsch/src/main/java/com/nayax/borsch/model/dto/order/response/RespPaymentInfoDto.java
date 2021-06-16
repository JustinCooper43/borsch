package com.nayax.borsch.model.dto.order.response;

import com.nayax.borsch.model.dto.user.response.RespCashierDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;

public class RespPaymentInfoDto {
    //TODO unmock values
    RespUserDto cashier = new RespUserDto();
    //TODO has to be different type to specify exact applied payment method
    RespCashierDto paymentMethod = new RespCashierDto();
    private Boolean completed = true;
    private Boolean confirmed = false;

    public RespUserDto getCashier() {
        return cashier;
    }

    public void setCashier(RespUserDto cashier) {
        this.cashier = cashier;
    }

    public RespCashierDto getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(RespCashierDto paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }
}
