package com.nayax.borsch.model.dto.order.request;

import java.time.LocalDateTime;

public class ReqOrderStartDto {
    Long userId;
    LocalDateTime endTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
