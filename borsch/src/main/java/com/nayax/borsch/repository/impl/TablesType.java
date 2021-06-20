package com.nayax.borsch.repository.impl;

public enum TablesType {

    EXTRA("ExtraItem"),
    ADDITION("Addition"),
    REM("Remark");

    String nameTable;

    TablesType(String nameTable) {
        this.nameTable = nameTable;
    }
}
