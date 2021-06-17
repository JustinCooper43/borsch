package com.nayax.borsch.model.dto.user.response;

import com.nayax.borsch.model.dto.user.response.nested.CreditCardDto;

public class RespCashierDto {

    private Long cashierId;
    private Boolean cashPaymentAllowed;
    private Boolean creditPaymentAllowed;
    private CreditCardDto creditCard;


    public Boolean getCashPaymentAllowed() {
        return cashPaymentAllowed;
    }

    public void setCashPaymentAllowed(Boolean cashPaymentAllowed) {
        this.cashPaymentAllowed = cashPaymentAllowed;
    }

    public CreditCardDto getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardDto creditCard) {
        this.creditCard = creditCard;
    }


    public Long getCashierId() {
        return cashierId;
    }
    public void setCashierId(Long cashierId) {
        this.cashierId = cashierId;
    }

    public Boolean getCreditPaymentAllowed() {
        return creditPaymentAllowed;
    }

    public void setCreditPaymentAllowed(Boolean creditPaymentAllowed) {
        this.creditPaymentAllowed = creditPaymentAllowed;
    }
}
