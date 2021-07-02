package com.nayax.borsch.validation.config;

import com.nayax.borsch.model.dto.order.request.ReqPayConfirmDto;
import com.nayax.borsch.validation.Validator;
import com.nayax.borsch.validation.componentimpl.SimpleValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.impl.ValidatorImpl;

import java.math.BigDecimal;
import java.util.List;

public class PaymentConfirmationValidationConfig {
    private static final Validator paymentValidator = new ValidatorImpl();

    static {
        paymentValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.CONFIRM_PAYMENT),
                obj -> ((ReqPayConfirmDto) obj).getUserId() != null && ((ReqPayConfirmDto) obj).getUserId() > 0, "Id is invalid", "id"));
        paymentValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.CONFIRM_PAYMENT),
                obj -> ((ReqPayConfirmDto) obj).getUserId() != null, "Id is null", "id"));
        paymentValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.CONFIRM_PAYMENT),
                obj -> ((ReqPayConfirmDto) obj).getOrderDate() != null, "Date is null", "date"));
        paymentValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.CONFIRM_PAYMENT),
                obj -> ((ReqPayConfirmDto) obj).getPaid() != null, "Paid is null", "paid"));
        paymentValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.CONFIRM_PAYMENT),
                obj -> ((ReqPayConfirmDto) obj).getPaid() != null && ((ReqPayConfirmDto) obj).getPaid().compareTo(BigDecimal.ZERO) != 0,
                "Paid is zero", "paid"));

    }

    public static Validator getPaymentValidator() {
        return paymentValidator;
    }
}
