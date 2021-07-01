package com.nayax.borsch.model.entity.user;

public class CashierEntity {
    private Long id;
    private Long userId;
    private boolean cashPaymentAllowed;
    private String cardNumber;
    private String cardBank;
    private String cardNote;
    private String cardQrCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isCashPaymentAllowed() {
        return cashPaymentAllowed;
    }

    public void setCashPaymentAllowed(boolean cashPaymentAllowed) {
        this.cashPaymentAllowed = cashPaymentAllowed;
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

    @Override
    public String toString() {
        return "CashierEntity{" +
                "userId=" + userId +
                ", cashPaymentAllowed=" + cashPaymentAllowed +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardBank='" + cardBank + '\'' +
                ", cardNote='" + cardNote + '\'' +
                ", cardQrCode='" + cardQrCode + '\'' +
                '}';
    }
}
