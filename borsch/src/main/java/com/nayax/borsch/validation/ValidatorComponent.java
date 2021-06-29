package com.nayax.borsch.validation;

import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.validation.enums.ValidationAction;

public interface ValidatorComponent {

    ErrorDto validate(Object obj);

    ValidationAction validAction();
}
