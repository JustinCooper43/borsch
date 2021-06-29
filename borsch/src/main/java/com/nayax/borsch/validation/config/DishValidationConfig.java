package com.nayax.borsch.validation.config;

import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.repository.impl.RepositoryShawarmaTypeImpl;
import com.nayax.borsch.validation.Validator;
import com.nayax.borsch.validation.componentimpl.SimpleValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.impl.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DishValidationConfig {
    private static final Validator dishValidator = new ValidatorImpl();
    @Autowired
    RepositoryShawarmaTypeImpl repositoryShawarmaType;
     {

        dishValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DISH_UPDATE, ValidationAction.DISH_DEL),
                obj -> repositoryShawarmaType.findById(((ReqSimplePriceItemUpDto) obj).getId()).get().getId() != null,
                "Dish id is null"));
    }

    public static Validator getValidator() {
        return dishValidator;
    }
}
