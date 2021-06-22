package com.nayax.borsch.repository.impl;

public enum TablesType {

    EXTRAITEM("ExtraItem"),
    ADDITION("Addition"),
    REMARK("Remark");

    String nameTable;

    TablesType(String nameTable) {
        this.nameTable = nameTable;
    }
}
