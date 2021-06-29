package com.nayax.borsch.validation.config;

import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.validation.Validator;
import com.nayax.borsch.validation.componentimpl.SimpleValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.impl.ValidatorImpl;

import java.math.BigDecimal;
import java.util.List;

public class DrinkAdditionValidationConfig {

    private static final Validator validatorDrinkAdd = new ValidatorImpl();

    static {
        validatorDrinkAdd.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_ADD,
                ValidationAction.DRINK_ADD, ValidationAction.DISH_ADD),
                obj -> ((ReqSimplePriceItemAddDto) obj).getName() != null,
                "Item's name is null"));

        validatorDrinkAdd.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_ADD,
                ValidationAction.DRINK_ADD, ValidationAction.DISH_ADD),
                obj -> ((ReqSimplePriceItemAddDto) obj).getPrice() != null,
                "Item's price is null"));

        validatorDrinkAdd.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_ADD,
                ValidationAction.DRINK_ADD, ValidationAction.DISH_ADD),
                obj -> ((ReqSimplePriceItemAddDto) obj).getName() != null && ((ReqSimplePriceItemAddDto) obj).getName().length() > 0,
                "Item's name is empty"));

        validatorDrinkAdd.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_ADD,
                ValidationAction.DRINK_ADD, ValidationAction.DISH_ADD),
                obj -> ((ReqSimplePriceItemAddDto) obj).getName() != null && !((ReqSimplePriceItemAddDto) obj).getName().matches("^[~@#\\$%\\^&\\*:;<>\\.,/}\\{\\+]"),
                "Item's name contains special characters"));

        validatorDrinkAdd.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_ADD,
                ValidationAction.DRINK_ADD, ValidationAction.DISH_ADD),
                obj -> ((ReqSimplePriceItemAddDto) obj).getPrice() != null && ((ReqSimplePriceItemAddDto) obj).getPrice().compareTo(BigDecimal.ZERO) > 0,
                "Item's price is negative number"));

        validatorDrinkAdd.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_UPDATE,
                ValidationAction.DRINK_UPDATE, ValidationAction.DISH_UPDATE),
                obj -> ((ReqSimplePriceItemUpDto) obj).getPrice() != null && ((ReqSimplePriceItemUpDto) obj).getPrice().compareTo(BigDecimal.ZERO) > 0,
                "Price of updated item is negative number"));

        validatorDrinkAdd.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_UPDATE,
                ValidationAction.DRINK_UPDATE, ValidationAction.DISH_UPDATE),
                obj -> ((ReqSimplePriceItemUpDto) obj).getPrice() != null ,
                "Price of updated item is null"));

        validatorDrinkAdd.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_UPDATE,
                ValidationAction.DRINK_UPDATE, ValidationAction.DISH_UPDATE),
                obj -> ((ReqSimplePriceItemUpDto) obj).getName() != null && ((ReqSimplePriceItemUpDto) obj).getName().length() > 0,
                "Name of updated item is empty"));

//        validatorDrinkAdd.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_UPDATE,
//                ValidationAction.DRINK_UPDATE, ValidationAction.DISH_UPDATE),
//                obj -> ((ReqSimplePriceItemUpDto) obj).getName() != null && ((ReqSimplePriceItemUpDto) obj).getName().matches("^[~@#\\$%\\^&\\*:;<>\\.,/}\\{\\+]"),
//                "Name of updated item contains special characters"));

        validatorDrinkAdd.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_UPDATE,
                ValidationAction.DRINK_UPDATE, ValidationAction.DISH_UPDATE),
                obj -> ((ReqSimplePriceItemUpDto) obj).getName() != null,
                "Name of updated item is null"));

        validatorDrinkAdd.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_UPDATE,
                ValidationAction.DRINK_UPDATE, ValidationAction.DISH_UPDATE),
                obj -> ((ReqSimplePriceItemUpDto) obj).getId() != null,
                "Id of updated item is null"));

        validatorDrinkAdd.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_UPDATE,
                ValidationAction.DRINK_UPDATE, ValidationAction.DISH_UPDATE),
                obj -> ((ReqSimplePriceItemUpDto) obj).getName() != null &&  ((ReqSimplePriceItemUpDto) obj).getId() > 0,
                "Id of updated item is invalid"));

    }

    public static Validator getValidatorDrinkAdd() {
        return validatorDrinkAdd;
    }
}
