package com.nayax.borsch.model.entity.user;

public class CashierEntity {

    private Long cashierId;
    private boolean cashPaymentAllowed;
    private boolean creditPaymentAllowed;

    private String cardNumber;
    private String cardBank;
    private String cardNote;
    private String cardQrCode;

    public Long getCashierId() {
        return cashierId;
    }

    public void setCashierId(Long cashierId) {
        this.cashierId = cashierId;
    }

    public boolean isCashPaymentAllowed() {
        return cashPaymentAllowed;
    }

    public void setCashPaymentAllowed(boolean cashPaymentAllowed) {
        this.cashPaymentAllowed = cashPaymentAllowed;
    }

    public boolean isCreditPaymentAllowed() {
        return creditPaymentAllowed;
    }

    public void setCreditPaymentAllowed(boolean creditPaymentAllowed) {
        this.creditPaymentAllowed = creditPaymentAllowed;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardBank() {
        return cardBank;
    }

    public void setCardBank(String cardBank) {
        this.cardBank = cardBank;
    }

    public String getCardNote() {
        return cardNote;
    }

    public void setCardNote(String cardNote) {
        this.cardNote = cardNote;
    }

    public String getCardQrCode() {
        return cardQrCode;
    }

    public void setCardQrCode(String cardQrCode) {
        this.cardQrCode = cardQrCode;
    }
}
