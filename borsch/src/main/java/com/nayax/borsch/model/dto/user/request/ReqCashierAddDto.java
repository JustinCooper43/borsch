package com.nayax.borsch.model.dto.user.request;

import com.nayax.borsch.model.dto.user.response.nested.CreditCardDto;

public class ReqCashierAddDto {

    private Boolean cash;
    private CreditCardDto card;

    public Boolean getCash() {
        return cash;
    }

    public void setCash(Boolean cash) {
        this.cash = cash;
    }

    public CreditCardDto getCard() {
        return card;
    }

    public void setCard(CreditCardDto card) {
        this.card = card;
    }
}
