package com.nayax.borsch.model.entity.payment;

import java.math.BigDecimal;
import java.util.List;

public class TotalPaymentCalcEntity {
    List<PaymentPerOrder> payments;
    BigDecimal orderCost;

    public List<PaymentPerOrder> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentPerOrder> payments) {
        this.payments = payments;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }
}
