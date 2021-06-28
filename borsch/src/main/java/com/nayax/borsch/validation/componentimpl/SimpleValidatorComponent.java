package com.nayax.borsch.validation.componentimpl;

import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.validation.ValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SimpleValidatorComponent implements ValidatorComponent {

    private final ValidationAction action;
    private final Predicate<Object> validationPredicate;
    private final String cause;

    public SimpleValidatorComponent(ValidationAction action, Predicate<Object> validationPredicate, String cause) {
        this.action = action;
        this.validationPredicate = validationPredicate;
        this.cause = cause;
    }

    public static List<? extends SimpleValidatorComponent> getComponents(List<ValidationAction> actions, Predicate<Object> validationPredicate, String cause) {
        return actions.stream().map(a -> new SimpleValidatorComponent(a, validationPredicate, cause)).collect(Collectors.toList());
    }

    @Override
    public ErrorDto validate(Object obj) {
        return validationPredicate.test(obj) ? null : new ErrorDto(cause);
    }

    @Override
    public ValidationAction validAction() {
        return action;
    }
}
