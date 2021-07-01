package com.nayax.borsch.validation.componentimpl;

import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.validation.ValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class SimpleValidatorComponent implements ValidatorComponent {

    private final ValidationAction action;
    private final Predicate<Object> validationPredicate;
    private final String cause;
    private final String field;

    public SimpleValidatorComponent(ValidationAction action, Predicate<Object> validationPredicate, String cause, String field) {
        this.action = action;
        this.validationPredicate = validationPredicate;
        this.cause = cause;
        this.field = field;
    }

    public static List<? extends SimpleValidatorComponent> getComponents(List<ValidationAction> actions, Predicate<Object> validationPredicate, String cause, String field) {
        return actions.stream().map(a -> new SimpleValidatorComponent(a, validationPredicate, cause, field)).collect(Collectors.toList());
    }

    @Override
    public ErrorDto validate(Object obj) {
        return validationPredicate.test(obj) ? null : new ErrorDto(cause,field);
    }

    @Override
    public ValidationAction validAction() {
        return action;
    }
}
