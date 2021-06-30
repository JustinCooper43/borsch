package com.nayax.borsch.validation.testvalid.config;

import com.nayax.borsch.model.dto.assortment.request.ReqSimpleItemUpDto;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.repository.impl.AdditionsRepository;
import com.nayax.borsch.repository.impl.TablesType;
import com.nayax.borsch.validation.Validator;
import com.nayax.borsch.validation.componentimpl.SimpleValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.impl.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConfigRepo {

    private static final Validator validatorRemark = new ValidatorImpl();

    @Autowired
    AdditionsRepository additionsRepository;

    {
        validatorRemark.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.REMARK_UPDATE),
                obj -> (additionsRepository.checkId(((ReqSimpleItemUpDto) obj).getId(), TablesType.REMARK)),
                "Id of updated item is invalid"));
    }

    public static Validator getValidatorRemark() {
        return validatorRemark;
    }
}
