package com.nayax.borsch.model.dto.user.response;

public class ProfileDto {
    //TODO unmock values
    private UserResponseDto user = new UserResponseDto();
    private CashierDto payments = new CashierDto();

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }

    public CashierDto getPayments() {
        return payments;
    }

    public void setPayments(CashierDto payments) {
        this.payments = payments;
    }
}
