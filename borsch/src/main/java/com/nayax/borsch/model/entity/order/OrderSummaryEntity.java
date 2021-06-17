package com.nayax.borsch.model.entity.order;

import com.nayax.borsch.model.entity.user.CashierEntity;

import java.time.LocalDateTime;

public class OrderSummaryEntity {
    private Long id;
    private CashierEntity cashier;
    private LocalDateTime startTime;
    private LocalDateTime stopTime;
    private LocalDateTime finishTime;

}
