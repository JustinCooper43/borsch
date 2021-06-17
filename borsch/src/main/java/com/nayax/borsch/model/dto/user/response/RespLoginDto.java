package com.nayax.borsch.model.dto.user.response;

import java.time.LocalDateTime;

public class RespLoginDto {
    private RespUserDto user;
    private LocalDateTime time;

    public RespUserDto getUser() {
        return user;
    }

    public void setUser(RespUserDto user) {
        this.user = user;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
