package com.nayax.borsch.model.dto.user.request;

import com.nayax.borsch.model.dto.user.response.nested.CreditCardDto;

public class ReqCashierUpDto {

    private Long cashierId;
    private Boolean cashPaymentAllowed;
    private CreditCardDto creditCard;
    private String qrCode;

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

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Long getCashierId() {
        return cashierId;
    }

    public void setCashierId(Long cashierId) {
        this.cashierId = cashierId;
    }
}
