package com.nayax.borsch.model.dto.user.response;

import com.nayax.borsch.model.dto.user.response.nested.RespLoginCashierDto;

import java.time.LocalDateTime;

public class RespLoginDto {
    private RespLoginCashierDto user;
    private LocalDateTime time;

    public RespLoginCashierDto getUser() {
        return user;
    }

    public void setUser(RespLoginCashierDto user) {
        this.user = user;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
