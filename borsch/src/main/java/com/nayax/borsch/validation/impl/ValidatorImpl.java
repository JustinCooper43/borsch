package com.nayax.borsch.validation.impl;

import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.Validator;
import com.nayax.borsch.validation.ValidatorComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ValidatorImpl implements Validator {

    private final List<ValidatorComponent> validator = new ArrayList<>();

    @Override
    public List<ErrorDto> validate(Object obj, ValidationAction action) {

        return validator.stream().filter(validatorComponent -> validatorComponent.validAction() == action)
                .map(validatorComponent -> validatorComponent.validate(obj))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void add(ValidatorComponent validatorComponent) {
        validator.add(validatorComponent);
    }

    @Override
    public void add(List<? extends ValidatorComponent> validatorComponents) {
        validator.addAll(validatorComponents);
    }
}
