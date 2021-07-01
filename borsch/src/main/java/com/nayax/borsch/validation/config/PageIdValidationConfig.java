package com.nayax.borsch.validation.config;

import com.nayax.borsch.validation.Validator;
import com.nayax.borsch.validation.componentimpl.SimpleValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.impl.ValidatorImpl;

import java.util.List;
import java.util.Objects;

public class PageIdValidationConfig {

    private static final Validator validatorPageId = new ValidatorImpl();

    static {
        validatorPageId.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PAGING), Objects::nonNull,
                "Parameters of page are null", "paging"));
        validatorPageId.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PAGING), obj -> obj != null
                && (Integer) obj > 0, "Parameters of page must be grate than 0", "paging"));
    }

    public static Validator getValidatorPageId() {
        return validatorPageId;
    }
}
