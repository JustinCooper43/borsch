package com.nayax.borsch.repository.impl;

public enum TablesType {

    EXTRAITEM("ExtraItem"),
    ADDITION("Addition"),
    REMARK("Remark"),
    SHAWARMA("ShawarmaType"),
    ORDER("Order"),
    USER("User"),
    ORDER_SUMMARY("OrderSummary"),
    PAYMENT("Payment"),
    CASHIER("Cashier"),
    ROLE("Role");

    public  String nameTable;

    TablesType(String nameTable) {
        this.nameTable = nameTable;
    }
}
