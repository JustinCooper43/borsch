package com.nayax.borsch.validation.config;

import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.repository.impl.RepositoryShawarmaTypeImpl;
import com.nayax.borsch.validation.Validator;
import com.nayax.borsch.validation.componentimpl.SimpleValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.impl.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

public class DishValidationConfig {

    private static final Validator dishValidator = new ValidatorImpl();

     static {

        dishValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DISH_UPDATE),
                obj -> ((ReqSimplePriceItemUpDto) obj).getId() != null, "Id is null", "id"));
        dishValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DISH_UPDATE),
                obj -> ((ReqSimplePriceItemUpDto) obj).getId() != null && ((ReqSimplePriceItemUpDto) obj).getId() > 0, "Id is not positive", "id"));
        dishValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DISH_UPDATE),
                obj -> ((ReqSimplePriceItemUpDto) obj).getName() != null, "Name is null", "Name"));
        dishValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DISH_UPDATE),
                obj -> ((ReqSimplePriceItemUpDto) obj).getPrice() != null, "Price is null", "Price"));
        dishValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DISH_UPDATE),
                obj -> ((ReqSimplePriceItemUpDto) obj).getPrice().compareTo(BigDecimal.ZERO) > 0, "Price less than zero", "Price"));
         dishValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DISH_UPDATE),
                 obj -> ((ReqSimplePriceItemUpDto) obj).getName() != null && ((ReqSimplePriceItemUpDto) obj).getName().length() > 0,
                 "Item's name is empty", "name"));



         dishValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DISH_ADD),
                 obj -> ((ReqSimplePriceItemAddDto) obj).getName() != null, "Name is null", "Name"));
         dishValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DISH_ADD),
                 obj -> ((ReqSimplePriceItemAddDto) obj).getPrice() != null, "Price is null", "Price"));
         dishValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DISH_ADD),
                 obj -> ((ReqSimplePriceItemAddDto) obj).getPrice().compareTo(BigDecimal.ZERO) > 0, "Price less than zero", "Price"));
         dishValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DISH_ADD),
                 obj -> ((ReqSimplePriceItemAddDto) obj).getName() != null && ((ReqSimplePriceItemAddDto) obj).getName().length() > 0,
                 "Item's name is empty", "name"));
    }

    public static Validator getValidator() {
        return dishValidator;
    }
}
