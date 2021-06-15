package com.nayax.borsch.model.dto.user.response.nested;

public class CreditCardDto {
    //TODO unmock values
    private String number = "1234567898765432";
    private String bank = "BankName";
    private String note = "Any possible text note about a card";

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
