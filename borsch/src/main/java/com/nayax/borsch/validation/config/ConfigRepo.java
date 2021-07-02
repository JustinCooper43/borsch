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

import java.time.LocalDateTime;
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

        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.REMARK_DEL, ValidationAction.REMARK_VERIFY_ID),
                obj -> (validationUtilRepository.checkId(((Long) obj), TablesType.REMARK)),
                "Remark ID doesn't exist", "id"));
        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ADDITIONS_DEL, ValidationAction.ADDITIONS_VERIFY_ID),
                obj -> (validationUtilRepository.checkId(((Long) obj), TablesType.ADDITION)),
                "Addition ID doesn't exist", "id"));
        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DRINK_DEL, ValidationAction.DRINK_VERIFY_ID),
                obj -> (validationUtilRepository.checkId(((Long) obj), TablesType.EXTRAITEM)),
                "Drink ID doesn't exist", "id"));
        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.DISH_DELETE, ValidationAction.DISH_VERIFY_ID),
                obj -> (validationUtilRepository.checkId(((Long) obj), TablesType.SHAWARMA)),
                "Dish ID doesn't exist", "id"));
        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.USER_VERIFY_ID,ValidationAction.USER_VERIFY_CASHIER),
                obj -> (validationUtilRepository.checkId(((Long) obj), TablesType.USER)),
                "No user found by given ID", "id"));
        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.ORDER_VERIFY_ID),
                obj -> (validationUtilRepository.checkId(((Long) obj), TablesType.ORDER)),
                "No order found by given ID", "id"));

        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.USER_ADD_EMAIL),
                obj -> (validationUtilRepository.checkEmail((String) obj)),
                "Email's item already exists", "email"));

        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.SUMM_ORDER_OPEN),
                obj -> (validationUtilRepository.checkDateTimeAfterCurrentOrderStart((LocalDateTime) obj)),
                "No open summary orders by the moment", "orderDate"));

        repositoryValidator.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.USER_VERIFY_CASHIER),
                obj -> (validationUtilRepository.checkCashierForUserId(((Long) obj))),
                "There is no active payment data for given userID", "id"));

    }

    public static Validator getRepositoryValidator() {
        return repositoryValidator;
    }
}
