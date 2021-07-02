package com.nayax.borsch.validation.config;

import com.nayax.borsch.model.dto.user.request.ReqProfileAddDto;
import com.nayax.borsch.model.dto.user.request.ReqProfileUpDto;
import com.nayax.borsch.model.dto.user.request.ReqUserAddDto;
import com.nayax.borsch.repository.impl.TablesType;
import com.nayax.borsch.validation.Validator;
import com.nayax.borsch.validation.componentimpl.SimpleValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.impl.ValidatorImpl;
import com.nayax.borsch.validation.testvalid.ValidationUtilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ProfileConfigValid {

    private static final Validator validatorProfile = new ValidatorImpl();

    @Autowired
    ValidationUtilRepository validationUtilRepository;

{

        //ValidationAction.PROFILE_ADD

        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> ((ReqProfileAddDto) obj).getUser().getFirstName() != null, "First Name is null", "FirstName" ));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> ((ReqProfileAddDto) obj).getUser().getFirstName() != null && !((ReqProfileAddDto) obj).getUser().getFirstName().isEmpty()
                , "First Name is empty", "FirstName" ));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> ((ReqProfileAddDto) obj).getUser().getFirstName() != null && ((ReqProfileAddDto) obj).getUser().getFirstName().matches("[а-яёА-ЯЁA-Za-z]+")
                , "First Name is not valid", "FirstName" ));


        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> ((ReqProfileAddDto) obj).getUser().getLastName() != null, "Last Name is null", "LastName" ));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> ((ReqProfileAddDto) obj).getUser().getLastName() != null && !((ReqProfileAddDto) obj).getUser().getLastName().isEmpty()
                , "Last Name is  empty", "LastName" ));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> ((ReqProfileAddDto) obj).getUser().getLastName() != null && ((ReqProfileAddDto) obj).getUser().getLastName().matches("[а-яёА-ЯЁA-Za-z]+")
                , "Last Name is not valid", "LastName" ));


        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj-> ((ReqProfileAddDto) obj).getUser().geteMail() != null, "User email is null", "eMail")
        );
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj-> ((ReqProfileAddDto) obj).getUser().geteMail() != null &&  !((ReqProfileAddDto) obj).getUser().geteMail().isEmpty(),
                "User email is empty", "eMail"
        ));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj-> ((ReqProfileAddDto) obj).getUser().geteMail() != null &&  ((ReqProfileAddDto) obj).getUser().geteMail().matches(
                       "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}"), "User email is not Valid", "eMail")
        );



        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> ((ReqProfileAddDto) obj).getUser().getPhone() != null  &&
                        !((ReqProfileAddDto) obj).getUser().getPhone().isEmpty(), "Phone number is empty", "phone"));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> ((ReqProfileAddDto) obj).getUser().getPhone() != null && ((ReqProfileAddDto) obj).getUser().getPhone().matches(
                       "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$"), "Phone number is not valid", "phone"));



        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getCreditCard() != null, "Credit Card is null", "creditCard"));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getCreditCard() != null &&
                        !((ReqProfileAddDto) obj).getPayments().getCard().getCreditCard().isEmpty(), "Credit Card is empty", "creditCard"));
           validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getCreditCard() != null &&
                        ((ReqProfileAddDto) obj).getPayments().getCard().getCreditCard().matches("\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}|\\d{16}") ,
                   "Credit Card is not valid", "creditCard"));


        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getBankName() != null,
                "Bank name is null" , "bankName"));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getBankName() != null &&
                        !((ReqProfileAddDto) obj).getPayments().getCard().getBankName().isEmpty(),
                "Bank name is empty" , "bankName"));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getBankName() != null &&
                        ((ReqProfileAddDto) obj).getPayments().getCard().getBankName().matches("[а-яёА-ЯЁA-Za-z]+\\s[а-яёА-ЯЁA-Za-z]+"),
                "Bank name is not valid" , "bankName"));


//ValidationAction.PROFILE_UPDATE

        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
                obj -> ((ReqProfileUpDto) obj).getUser().getId() != null, "User id is null", "id"));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
                obj -> ((ReqProfileUpDto) obj).getUser().getId() != null && ((ReqProfileUpDto) obj).getUser().getId() > 0
                && ((ReqProfileUpDto) obj).getUser().getId().toString().matches("\\d+"), "User id is not valid", "id"));

        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
                obj -> ((ReqProfileUpDto) obj).getUser().getId() != null && validationUtilRepository.checkId(
                        ((ReqProfileUpDto) obj).getUser().getId(), TablesType.USER), "User id is not found", "id"));



    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
            obj -> ((ReqProfileAddDto) obj).getUser().getFirstName() != null, "First Name is null", "FirstName" ));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
            obj -> ((ReqProfileAddDto) obj).getUser().getFirstName() != null && !((ReqProfileAddDto) obj).getUser().getFirstName().isEmpty()
            , "First Name is empty", "FirstName" ));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
            obj -> ((ReqProfileAddDto) obj).getUser().getFirstName() != null && ((ReqProfileAddDto) obj).getUser().getFirstName().matches("[а-яёА-ЯЁA-Za-z]+")
            , "First Name is not valid", "FirstName" ));


    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
            obj -> ((ReqProfileAddDto) obj).getUser().getLastName() != null, "Last Name is null", "LastName" ));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
            obj -> ((ReqProfileAddDto) obj).getUser().getLastName() != null && !((ReqProfileAddDto) obj).getUser().getLastName().isEmpty()
            , "Last Name is  empty", "LastName" ));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
            obj -> ((ReqProfileAddDto) obj).getUser().getLastName() != null && ((ReqProfileAddDto) obj).getUser().getLastName().matches("[а-яёА-ЯЁA-Za-z]+")
            , "Last Name is not valid", "LastName" ));


    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
            obj-> ((ReqProfileAddDto) obj).getUser().geteMail() != null, "User email is null", "eMail")
    );
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
            obj-> ((ReqProfileAddDto) obj).getUser().geteMail() != null &&  !((ReqProfileAddDto) obj).getUser().geteMail().isEmpty(),
            "User email is empty", "eMail"
    ));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
            obj-> ((ReqProfileAddDto) obj).getUser().geteMail() != null &&  ((ReqProfileAddDto) obj).getUser().geteMail().matches(
                    "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}"), "User email is not Valid", "eMail")
    );



    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
            obj -> ((ReqProfileAddDto) obj).getUser().getPhone() != null  &&
                    !((ReqProfileAddDto) obj).getUser().getPhone().isEmpty(), "Phone number is empty", "phone"));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
            obj -> ((ReqProfileAddDto) obj).getUser().getPhone() != null && ((ReqProfileAddDto) obj).getUser().getPhone().matches(
                    "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$"), "Phone number is not valid", "phone"));



    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
            obj -> isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getCreditCard() != null, "Credit Card is null", "creditCard"));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
            obj -> isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getCreditCard() != null &&
                    !((ReqProfileAddDto) obj).getPayments().getCard().getCreditCard().isEmpty(), "Credit Card is empty", "creditCard"));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
            obj -> isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getCreditCard() != null &&
                    ((ReqProfileAddDto) obj).getPayments().getCard().getCreditCard().matches("\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}|\\d{16}") ,
            "Credit Card is not valid", "creditCard"));


    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
            obj -> isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getBankName() != null,
            "Bank name is null" , "bankName"));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
            obj -> isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getBankName() != null &&
                    !((ReqProfileAddDto) obj).getPayments().getCard().getBankName().isEmpty(),
            "Bank name is empty" , "bankName"));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_UPDATE),
            obj -> isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getBankName() != null &&
                    ((ReqProfileAddDto) obj).getPayments().getCard().getBankName().matches("[а-яёА-ЯЁA-Za-z]+\\s[а-яёА-ЯЁA-Za-z]+"),
            "Bank name is not valid" , "bankName"));



    //ValidationAction.PROFILE_GET


    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_GET),
            Objects::nonNull, "User id is null", "id"));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_GET),
            id -> id != null && id.toString().matches("\\d+"), "User id is not valid", "id"));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_GET),
            id -> id != null && validationUtilRepository.checkId((Long) id, TablesType.USER), "User id is not found", "id"));

    //ValidationAction.USER_ADD
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.USER_ADD),
            obj -> ((ReqUserAddDto) obj).getFirstName() != null, "First Name is null", "FirstName"));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.USER_ADD),
            obj -> ((ReqUserAddDto) obj).getFirstName() != null && !((ReqUserAddDto) obj).getFirstName().isEmpty()
            , "First Name is empty", "FirstName"));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.USER_ADD),
            obj -> ((ReqUserAddDto) obj).getFirstName() != null && ((ReqUserAddDto) obj).getFirstName().matches("[а-яёА-ЯЁA-Za-z]+")
            , "First Name is not valid", "FirstName"));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.USER_ADD),
            obj -> ((ReqUserAddDto) obj).getLastName() != null, "Last Name is null", "LastName"));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.USER_ADD),
            obj -> ((ReqUserAddDto) obj).getLastName() != null && !((ReqUserAddDto) obj).getLastName().isEmpty()
            , "Last Name is  empty", "LastName"));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.USER_ADD),
            obj -> ((ReqUserAddDto) obj).getLastName() != null && ((ReqUserAddDto) obj).getLastName().matches("[а-яёА-ЯЁA-Za-z]+")
            , "Last Name is not valid", "LastName"));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.USER_ADD),
            obj -> ((ReqUserAddDto) obj).geteMail() != null, "User email is null", "eMail")
    );
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.USER_ADD),
            obj -> ((ReqUserAddDto) obj).geteMail() != null && !((ReqUserAddDto) obj).geteMail().isEmpty(),
            "User email is empty", "eMail"
    ));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.USER_ADD),
            obj -> ((ReqUserAddDto) obj).geteMail() != null && ((ReqUserAddDto) obj).geteMail()
                    .matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}"),
            "User email is not Valid", "eMail"));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.USER_ADD),
            obj -> ((ReqUserAddDto) obj).getPhone() != null &&
                    ((ReqUserAddDto) obj).getPhone().length() > 9, "Phone number is empty", "phone"));
    validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.USER_ADD),
            obj -> ((ReqUserAddDto) obj).getPhone() != null && ((ReqUserAddDto) obj).getPhone().matches(
                    "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$"), "Phone number is not valid", "phone"));

}


    public static boolean isCashier(ReqProfileAddDto dto){

        return dto.getPayments().getCash();
    }

    public static Validator getValidatorProfile() {
        return validatorProfile;
    }
}
