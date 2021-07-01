package com.nayax.borsch.validation.config;

import com.nayax.borsch.model.dto.user.request.ReqProfileAddDto;
import com.nayax.borsch.validation.Validator;
import com.nayax.borsch.validation.componentimpl.SimpleValidatorComponent;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.impl.ValidatorImpl;

import java.util.List;

public class ProfileConfigValid {

    private static final Validator validatorProfile = new ValidatorImpl();


    static {

        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> ((ReqProfileAddDto) obj).getUser().getFirstName() != null, "First Name is null", "FirstName" ));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> ((ReqProfileAddDto) obj).getUser().getFirstName() != null && !((ReqProfileAddDto) obj).getUser().getFirstName().isEmpty()
                , "First Name is empty", "FirstName" ));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> ((ReqProfileAddDto) obj).getUser().getFirstName() != null && !((ReqProfileAddDto) obj).getUser().getFirstName().matches("\\W|_|\\s")
                , "First Name is not valid", "FirstName" ));


        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> ((ReqProfileAddDto) obj).getUser().getLastName() != null, "Last Name is null", "LastName" ));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> ((ReqProfileAddDto) obj).getUser().getLastName() != null && !((ReqProfileAddDto) obj).getUser().getLastName().isEmpty()
                , "Last Name is  empty", "LastName" ));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> ((ReqProfileAddDto) obj).getUser().getLastName() != null && !((ReqProfileAddDto) obj).getUser().getLastName().matches("\\W|_|\\s")
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
                obj -> ((ReqProfileAddDto) obj).getUser().getPhone() != null , "Phone number is null", "phone"));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> ((ReqProfileAddDto) obj).getUser().getPhone() != null  &&
                        !((ReqProfileAddDto) obj).getUser().getLastName().isEmpty(), "Phone number is empty", "phone"));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> ((ReqProfileAddDto) obj).getUser().getPhone() != null && ((ReqProfileAddDto) obj).getUser().getPhone().matches(
                       "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$"), "Phone number is not valid", "phone"));



        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> !isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getCreditCard() != null, "Credit Card is null", "creditCard"));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> !isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getCreditCard() != null &&
                        !((ReqProfileAddDto) obj).getPayments().getCard().getCreditCard().isEmpty(), "Credit Card is empty", "creditCard"));
           validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> !isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getCreditCard() != null &&
                        ((ReqProfileAddDto) obj).getPayments().getCard().getCreditCard().matches("\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}|\\d{16}") ,
                   "Credit Card is not valid", "creditCard"));


        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> !isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getBankName() != null,
                "Bank name is null" , "bankName"));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> !isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getBankName() != null &&
                        ((ReqProfileAddDto) obj).getPayments().getCard().getBankName().isEmpty(),
                "Bank name is empty" , "bankName"));
        validatorProfile.add(SimpleValidatorComponent.getComponents(List.of(ValidationAction.PROFILE_ADD),
                obj -> !isCashier((ReqProfileAddDto) obj) && ((ReqProfileAddDto) obj).getPayments().getCard().getBankName() != null &&
                        ((ReqProfileAddDto) obj).getPayments().getCard().getBankName().matches("[#@!$%^&*(){}+=-><.,\\/'\\\"~`:;№?_|]"),
                "Bank name is not valid" , "bankName"));




    }


    public static boolean isCashier(ReqProfileAddDto dto){

        return dto.getPayments().getCash();
    }

    public static Validator getValidatorProfile() {
        return validatorProfile;
    }
}
