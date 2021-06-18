package com.nayax.borsch.model.entity.payment;

import com.nayax.borsch.model.entity.user.CashierEntity;
import com.nayax.borsch.model.entity.user.UserEntity;

public class PaymentInfoEntity {
    UserEntity cashier;
    CashierEntity paymentMethod;
    private boolean completed;
    private boolean confirmed;

    public UserEntity getCashier() {
        return cashier;
    }

    public void setCashier(UserEntity cashier) {
        this.cashier = cashier;
    }

    public CashierEntity getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(CashierEntity paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
