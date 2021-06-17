package com.nayax.borsch.model.entity.order;

import com.nayax.borsch.model.entity.user.CashierEntity;

import java.time.LocalDateTime;

public class OrderSummaryEntity {

    private Long id;
    private CashierEntity cashier;
    private LocalDateTime startTime;
    private LocalDateTime stopTime;
    private LocalDateTime finishTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CashierEntity getCashier() {
        return cashier;
    }

    public void setCashier(CashierEntity cashier) {
        this.cashier = cashier;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(LocalDateTime stopTime) {
        this.stopTime = stopTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }
}
