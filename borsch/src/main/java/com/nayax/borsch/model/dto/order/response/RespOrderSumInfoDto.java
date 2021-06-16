package com.nayax.borsch.model.dto.order.response;

import java.math.BigDecimal;

public class RespOrderSumInfoDto {

    private BigDecimal payAmount;
    private BigDecimal payCompleted;
    private BigDecimal payConfirmed;
    private BigDecimal payUnconfirmed;

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getPayCompleted() {
        return payCompleted;
    }

    public void setPayCompleted(BigDecimal payCompleted) {
        this.payCompleted = payCompleted;
    }

    public BigDecimal getPayConfirmed() {
        return payConfirmed;
    }

    public void setPayConfirmed(BigDecimal payConfirmed) {
        this.payConfirmed = payConfirmed;
    }

    public BigDecimal getPayUnconfirmed() {
        return payUnconfirmed;
    }

    public void setPayUnconfirmed(BigDecimal payUnconfirmed) {
        this.payUnconfirmed = payUnconfirmed;
    }
}
