package com.nayax.borsch.model.dto.order.response;

import com.nayax.borsch.model.dto.enums.OrderSummaryStatus;

import java.time.LocalDateTime;

public class RespOrderStatusDto {

    OrderSummaryStatus status;
    LocalDateTime endTime;

    public OrderSummaryStatus getStatus() {
        return status;
    }

    public void setStatus(OrderSummaryStatus status) {
        this.status = status;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
