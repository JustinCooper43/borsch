package com.nayax.borsch.model.dto.user.response.nested;

import com.nayax.borsch.model.dto.user.response.RespUserDto;

public class RespLoginCashierDto extends RespUserDto {
    private boolean isCashier;

    public boolean isCashier() {
        return isCashier;
    }

    public void setCashier(boolean cashier) {
        isCashier = cashier;
    }

}
