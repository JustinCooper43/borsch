package com.nayax.borsch.model.entity.payment;

import java.math.BigDecimal;

public class PaymentPerOrder {
    Long paymentId;
    boolean confirmed;
    boolean completed;
    BigDecimal payedSum;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public BigDecimal getPayedSum() {
        return payedSum;
    }

    public void setPayedSum(BigDecimal payedSum) {
        this.payedSum = payedSum;
    }
}
