package com.nayax.borsch.model.dto.user.response;

import java.time.LocalDateTime;

public class LoginResponseDto {
    //TODO unmock values
    private UserResponseDto user = new UserResponseDto();
    private LocalDateTime time = LocalDateTime.now();

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
