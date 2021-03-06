package com.nayax.borsch.validation.config;

import com.nayax.borsch.model.dto.assortment.request.ReqAssortmentUpDto;
import com.nayax.borsch.model.dto.order.request.ReqOrderItemAddDto;
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
                obj -> ((ReqAssortmentUpDto) obj).getDish() != null && ((ReqAssortmentUpDto) obj).getDish() > 0, "Id dish must be grate than 0", "Dish"));

        assortmentValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ASSORTMENT_UPDATE),
                obj -> ((ReqAssortmentUpDto) obj).getAdditions() != null
                        && ((ReqAssortmentUpDto) obj).getAdditions().size() > 0
                        && ((ReqAssortmentUpDto) obj).getAdditions().stream()
                        .allMatch(i -> i != null && i > 0),
                "Addition ID not positive", "additions"));

        assortmentValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ASSORTMENT_UPDATE),
                obj -> ((ReqAssortmentUpDto) obj).getRemarks() != null
                        && ((ReqAssortmentUpDto) obj).getRemarks().size() > 0
                        && ((ReqAssortmentUpDto) obj).getRemarks().stream()
                        .allMatch(i -> i != null && i > 0),
                "Remarks ID not positive", "Remarks"));


    }

    public static Validator getValidator() {
        return assortmentValidator;
    }
}
