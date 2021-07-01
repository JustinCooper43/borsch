package com.nayax.borsch.validation.config;

import com.nayax.borsch.model.dto.assortment.request.ReqSimpleItemUpDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.repository.impl.TablesType;
import com.nayax.borsch.validation.Validator;
import com.nayax.borsch.validation.componentimpl.SimpleValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.impl.ValidatorImpl;
import com.nayax.borsch.validation.testvalid.ValidationUtilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConfigRepo {

    private static final Validator repositoryValidator = new ValidatorImpl();

    @Autowired
    ValidationUtilRepository validationUtilRepository;

    {
        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.REMARK_UPDATE),
                obj -> (validationUtilRepository.checkId(((ReqSimpleItemUpDto) obj).getId(), TablesType.REMARK)),
                "Id of updated item doesn't exist", "id"));
        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_UPDATE),
                obj -> (validationUtilRepository.checkId(((ReqSimplePriceItemUpDto) obj).getId(), TablesType.ADDITION)),
                "Id of updated item doesn't exist", "id"));
        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DRINK_UPDATE),
                obj -> (validationUtilRepository.checkId(((ReqSimplePriceItemUpDto) obj).getId(), TablesType.EXTRAITEM)),
                "Id of updated item doesn't exist", "id"));

        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.REMARK_DEL),
                obj -> (validationUtilRepository.checkId(((Long) obj), TablesType.REMARK)),
                "Id of updated Remark doesn't exist", "id"));
        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_DEL),
                obj -> (validationUtilRepository.checkId(((Long) obj), TablesType.ADDITION)),
                "Id of updated Addition doesn't exist", "id"));
        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DRINK_DEL),
                obj -> (validationUtilRepository.checkId(((Long) obj), TablesType.EXTRAITEM)),
                "Id of updated item doesn't exist", "id"));
        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DRINK_DEL),
                obj -> (validationUtilRepository.checkId(((Long) obj), TablesType.SHAWARMA)),
                "Id of updated item doesn't exist", "id"));
        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DISH_DELETE),
                obj -> (validationUtilRepository.checkId(((Long) obj), TablesType.SHAWARMA)),
                "Id of updated item doesn't exist", "id"));

        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.USER_ADD_EMAIL),
                obj -> !(validationUtilRepository.checkEmail((String) obj)),
                "Email's item is exist", "email"));

    }

    public static Validator getRepositoryValidator() {
        return repositoryValidator;
    }
}
