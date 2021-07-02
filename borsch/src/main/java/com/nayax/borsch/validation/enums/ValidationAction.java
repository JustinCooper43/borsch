package com.nayax.borsch.validation.enums;

public enum ValidationAction {

    ADDITIONS_ADD, DRINK_ADD, SIMPLE_PRICE_ITEM_ADD, REMARK_ADD, DISH_ADD,
    ORDER_ITEM_ADD, ORDER_ITEM_DEL,

    ADDITIONS_UPDATE, DRINK_UPDATE, REMARK_UPDATE, DISH_UPDATE,
    ASSORTMENT_UPDATE,

    ADDITIONS_DEL, DRINK_DEL, SIMPLE_PRICE_ITEM_DEL, REMARK_DEL, DISH_DELETE,

    ADDITIONS_GETALL, DRINK_GETALL, REMARK_GETALL, DISH_GETALL,

    ADDITIONS_VERIFY_ID, DRINK_VERIFY_ID, REMARK_VERIFY_ID, DISH_VERIFY_ID,
    USER_VERIFY_ID, ORDER_VERIFY_ID,

    USER_ADD_EMAIL,
    ////////////////////
    PAGING,

    CONFIRM_PAYMENT, SUMM_ORDER_OPEN,

    DATE, DATETIME;
}
