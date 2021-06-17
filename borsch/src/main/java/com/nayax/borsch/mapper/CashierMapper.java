package com.nayax.borsch.mapper;


import com.nayax.borsch.model.dto.user.request.ReqCashierUpDto;
import com.nayax.borsch.model.dto.user.response.RespCashierDto;
import com.nayax.borsch.model.entity.user.CashierEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CashierMapper {

    @Mapping(source = "cardNumber", target = "card.creditCard")
    @Mapping(source = "cardBank", target = "card.bankName")
    @Mapping(source = "cardNote", target = "card.notes")
    @Mapping(source = "cardQrCode", target = "card.qr")
    @Mapping(source = "cashPaymentAllowed", target = "cash")
    RespCashierDto toDto(CashierEntity entity);



    @Mapping(target = "cardNumber", source = "card.creditCard")
    @Mapping(target = "cardBank", source = "card.bankName")
    @Mapping(target = "cardNote", source = "card.notes")
    @Mapping(target = "cardQrCode", source = "card.qr")
    @Mapping(target = "cashPaymentAllowed", source = "cash")
    CashierEntity toUpdateEntity(ReqCashierUpDto upDto);


}
