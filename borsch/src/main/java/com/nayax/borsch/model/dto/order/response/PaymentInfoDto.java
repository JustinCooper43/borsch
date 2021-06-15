package com.nayax.borsch.model.dto.order.response;

import com.nayax.borsch.model.dto.user.response.CashierDto;
import com.nayax.borsch.model.dto.user.response.UserResponseDto;

public class PaymentInfoDto {
    //TODO unmock values
    UserResponseDto cashier = new UserResponseDto();
    //TODO has to be different type to specify exact applied payment method
    CashierDto paymentMethod = new CashierDto();
    private Boolean completed = true;
    private Boolean confirmed = false;

    public UserResponseDto getCashier() {
        return cashier;
    }

    public void setCashier(UserResponseDto cashier) {
        this.cashier = cashier;
    }

    public CashierDto getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(CashierDto paymentMethod) {
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
