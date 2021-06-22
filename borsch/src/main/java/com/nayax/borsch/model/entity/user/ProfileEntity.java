package com.nayax.borsch.model.entity.user;

public class ProfileEntity {

    private CashierEntity cashierEntity;
    private UserEntity userEntity;

    public CashierEntity getCashierEntity() {
        return cashierEntity;
    }

    public void setCashierEntity(CashierEntity cashierEntity) {
        this.cashierEntity = cashierEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

}
