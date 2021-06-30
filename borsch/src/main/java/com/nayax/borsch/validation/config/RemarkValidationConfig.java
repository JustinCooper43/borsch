package com.nayax.borsch.validation.config;

import com.nayax.borsch.model.dto.assortment.request.ReqSimpleItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimpleItemUpDto;
import com.nayax.borsch.validation.Validator;
import com.nayax.borsch.validation.componentimpl.SimpleValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.impl.ValidatorImpl;

import java.util.List;

public class RemarkValidationConfig {

    private static final Validator validatorRemark = new ValidatorImpl();

    static{

        validatorRemark.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.REMARK_ADD),
                obj -> ((ReqSimpleItemAddDto) obj).getName() != null,
                "Item's name is null","name"));

        validatorRemark.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.REMARK_ADD),
                obj -> ((ReqSimpleItemAddDto) obj).getName() != null && ((ReqSimpleItemAddDto) obj).getName().length() > 0,
                "Item's name is empty","name"));

        validatorRemark.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.REMARK_ADD),
                obj -> ((ReqSimpleItemAddDto) obj).getName() != null && ((ReqSimpleItemAddDto) obj).getName().matches("^[~@#\\$%\\^&\\*:;<>\\.,/}\\{\\+]"),
                "Item's name contains special characters","name"));

        validatorRemark.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.REMARK_UPDATE),
                obj -> ((ReqSimpleItemUpDto) obj).getName() != null,
                "Name of updated item is null","name"));

        validatorRemark.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.REMARK_UPDATE),
                obj -> ((ReqSimpleItemUpDto) obj).getName() != null && ((ReqSimpleItemUpDto) obj).getName().length() > 0,
                "Name of updated item is empty","name"));

        validatorRemark.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.REMARK_UPDATE),
                obj -> ((ReqSimpleItemUpDto) obj).getName() != null && ((ReqSimpleItemUpDto) obj).getName().matches("^[~@#\\$%\\^&\\*:;<>\\.,/}\\{\\+]"),
                "Name of updated item contains special characters","name"));

        validatorRemark.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.REMARK_UPDATE),
                obj -> ((ReqSimpleItemUpDto) obj).getId() != null,
                "Id of updated item is null", "id"));

        validatorRemark.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.REMARK_UPDATE),
                obj -> ((ReqSimpleItemUpDto) obj).getId() != null && ((ReqSimpleItemUpDto) obj).getId() > 1,
                "Id of updated item is invalid", "id"));

    }

    public static Validator getValidatorRemark() {
        return validatorRemark;
    }
}
