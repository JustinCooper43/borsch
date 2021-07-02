package com.nayax.borsch.validation.config;

import com.nayax.borsch.model.dto.order.request.ReqOrderItemAddDto;
import com.nayax.borsch.validation.Validator;
import com.nayax.borsch.validation.componentimpl.SimpleValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.impl.ValidatorImpl;

import java.time.LocalDateTime;
import java.util.List;

public class OrderItemValidationConfig {
    private static final Validator orderItemValidator = new ValidatorImpl();

    static {
        orderItemValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ORDER_ITEM_ADD),
                obj -> ((ReqOrderItemAddDto) obj).getUserId() != null, "User ID not set", "userId"));
        orderItemValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ORDER_ITEM_ADD),
                obj -> ((ReqOrderItemAddDto) obj).getUserId() != null && ((ReqOrderItemAddDto) obj).getUserId() > 0,
                "User ID not positive", "userId"));
        orderItemValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ORDER_ITEM_ADD),
                obj -> ((ReqOrderItemAddDto) obj).getDish() != null, "Dish not set", "dish"));
        orderItemValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ORDER_ITEM_ADD),
                obj -> ((ReqOrderItemAddDto) obj).getDish() != null && ((ReqOrderItemAddDto) obj).getDish() > 0,
                "Dish ID not positive", "dish"));
        orderItemValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ORDER_ITEM_ADD),
                obj -> ((ReqOrderItemAddDto) obj).getDrink() != null && ((ReqOrderItemAddDto) obj).getDrink() > 0,
                "Drink ID not positive", "drink"));
        orderItemValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ORDER_ITEM_ADD),
                obj -> ((ReqOrderItemAddDto) obj).getRemark() != null && ((ReqOrderItemAddDto) obj).getRemark() > 0,
                "Remark ID not positive", "remark"));
        orderItemValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ORDER_ITEM_ADD),
                obj -> ((ReqOrderItemAddDto) obj).getQuantity() != null, "Quantity not set", "quantity"));
        orderItemValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ORDER_ITEM_ADD),
                obj -> ((ReqOrderItemAddDto) obj).getQuantity() != null && ((ReqOrderItemAddDto) obj).getQuantity() > 0,
                "Quantity not positive", "quantity"));
        orderItemValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ORDER_ITEM_ADD),
                obj -> ((ReqOrderItemAddDto) obj).getAdditions() != null, "Additions not set", "additions"));
        orderItemValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ORDER_ITEM_ADD),
                obj -> ((ReqOrderItemAddDto) obj).getAdditions() != null
                        && ((ReqOrderItemAddDto) obj).getAdditions().size() > 0
                        && ((ReqOrderItemAddDto) obj).getAdditions().stream()
                        .allMatch(i -> i != null && i > 0),
                "Addition ID not positive", "additions"));
        orderItemValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ORDER_ITEM_ADD),
                obj -> ((ReqOrderItemAddDto) obj).getOrderDate() != null, "Date not set", "date"));
        orderItemValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ORDER_ITEM_ADD),
                obj -> ((ReqOrderItemAddDto) obj).getOrderDate() != null
                        && ((ReqOrderItemAddDto) obj).getOrderDate().isBefore(LocalDateTime.now()),
                "Invalid date", "date"));

    }

    public static Validator getOrderItemValidator(){
        return orderItemValidator;
    }

}
