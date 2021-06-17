package com.nayax.borsch.model.entity.order;

import java.time.LocalDateTime;

public class OrderSummaryEntity {
    private Long id;
    private CashierEntity cashier;
    private LocalDateTime startTime;
    private LocalDateTime stopTime;
    private LocalDateTime finishTime;

}
