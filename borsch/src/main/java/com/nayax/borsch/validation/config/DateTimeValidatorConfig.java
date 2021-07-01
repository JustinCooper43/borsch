package com.nayax.borsch.validation.config;

import com.nayax.borsch.validation.Validator;
import com.nayax.borsch.validation.componentimpl.SimpleValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.impl.ValidatorImpl;

import java.util.List;
import java.util.Objects;

public class DateTimeValidatorConfig {
    private static final Validator dateTimeValidator = new ValidatorImpl();

    static {
        dateTimeValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DATE),
                Objects::nonNull, "Date is null", "date"));
        dateTimeValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DATE),
                obj -> obj != null && ((String) obj).length() > 0, "Date is empty", "date"));
        dateTimeValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DATE),
                obj -> obj != null && ((String) obj).length() == 10, "Date is too short", "date"));
        dateTimeValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DATE),
                obj -> ((String) obj).matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$"),
                "Invalid date format", "date"));

    }

    public static Validator getDateTimeValidator() {
        return dateTimeValidator;
    }
}
