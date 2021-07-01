package com.nayax.borsch.validation.config;

import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.repository.impl.AdditionsRepository;
import com.nayax.borsch.validation.Validator;
import com.nayax.borsch.validation.ValidatorComponent;
import com.nayax.borsch.validation.componentimpl.SimpleValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.impl.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

public class PageIdValidationConfig {

    private static final Validator validatorPageId = new ValidatorImpl();

    static {
        validatorPageId.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_GETALL,
                ValidationAction.DISH_GETALL, ValidationAction.DRINK_GETALL), Objects::nonNull,
                "Parameters of page are null", "paging"));
        validatorPageId.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_GETALL,
                ValidationAction.DISH_GETALL, ValidationAction.DRINK_GETALL), obj -> obj != null
                && (Integer) obj > 0, "Parameters of page are invalid", "paging"));
        validatorPageId.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_DEL,
                ValidationAction.REMARK_DEL, ValidationAction.DRINK_DEL), obj -> obj != null
                && (Long) obj > 0, "Id is invalid", "id"));

        validatorPageId.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_DEL,
                ValidationAction.REMARK_DEL, ValidationAction.DRINK_DEL, ValidationAction.DISH_DELETE), Objects::nonNull, "Id is null", "id"));
    }

    public static Validator getValidatorPageId() {
        return validatorPageId;
    }
}
