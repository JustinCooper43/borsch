package com.nayax.borsch.validation.config;

import com.nayax.borsch.model.dto.assortment.request.ReqAssortmentUpDto;
import com.nayax.borsch.repository.impl.RepositoryAssortmentImpl;
import com.nayax.borsch.validation.Validator;
import com.nayax.borsch.validation.componentimpl.SimpleValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.impl.ValidatorImpl;
import com.nayax.borsch.validation.testvalid.ValidationUtilRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AssortmentValidationConfig {

    private static final Validator assortmentValidator = new ValidatorImpl();


    {
        assortmentValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ASSORTMEN_UPDATE),
                obj -> ((ReqAssortmentUpDto) obj).getDish() != null, "Dish Id is null", "Dish"));
    }

    public static Validator getValidator() {
        return assortmentValidator;
    }
}
