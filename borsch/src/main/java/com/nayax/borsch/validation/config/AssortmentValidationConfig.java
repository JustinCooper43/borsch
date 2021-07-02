package com.nayax.borsch.validation.config;

import com.nayax.borsch.model.dto.assortment.request.ReqAssortmentUpDto;
import com.nayax.borsch.validation.Validator;
import com.nayax.borsch.validation.componentimpl.SimpleValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.impl.ValidatorImpl;

import java.util.List;

public class AssortmentValidationConfig {

    private static final Validator assortmentValidator = new ValidatorImpl();

    static {
        assortmentValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ASSORTMENT_UPDATE),
                obj -> ((ReqAssortmentUpDto) obj).getDish() != null, "Dish Id is null", "Dish"));
        assortmentValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ASSORTMENT_UPDATE),
                obj -> ((ReqAssortmentUpDto) obj).getDish() != null && ((ReqAssortmentUpDto) obj).getDish() > 0, "Id dish must be grate than 0", "paging"));

    }

    public static Validator getValidator() {
        return assortmentValidator;
    }
}
