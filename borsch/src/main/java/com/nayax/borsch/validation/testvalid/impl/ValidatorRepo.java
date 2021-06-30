package com.nayax.borsch.validation.testvalid.impl;

import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.validation.ValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;

import java.util.List;

public interface ValidatorRepo {

    List<ErrorDto> validate(Object obj1,Object obj2, ValidationAction action);

    void add(ValidatorComponent validatorComponent);

    void add(List<? extends ValidatorComponent> validatorComponents);
}
