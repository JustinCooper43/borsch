package com.nayax.borsch.validation;

import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.validation.enums.ValidationAction;

import java.util.List;

public interface Validator {

    List<ErrorDto> validate(Object obj, ValidationAction action);

    void add(ValidatorComponent validatorComponent);

    void add(List<? extends ValidatorComponent> validatorComponents);
}
