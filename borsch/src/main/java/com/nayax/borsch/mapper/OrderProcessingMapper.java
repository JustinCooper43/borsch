package com.nayax.borsch.mapper;

import com.nayax.borsch.model.dto.order.request.ReqPayConfirmDto;
import com.nayax.borsch.model.dto.order.response.RespOrderDeliveryDto;
import com.nayax.borsch.model.dto.order.response.RespOrderSumDto;
import com.nayax.borsch.model.dto.order.response.RespPaymentInfoDto;
import com.nayax.borsch.model.entity.order.OrderDeliveryEntity;
import com.nayax.borsch.model.entity.order.OrderSummaryEntity;
import com.nayax.borsch.model.entity.payment.PaymentConfirmation;
import com.nayax.borsch.model.entity.payment.PaymentInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {OrderItemMapper.class, UserMapper.class, CashierMapper.class, SimpleItemsMapper.class})
public interface OrderProcessingMapper {
    RespOrderDeliveryDto toOrderDelivery(OrderDeliveryEntity entity);

    @Mapping(source = "payedSum", target = "paidAmount")
    @Mapping(source = "totalOrdersCost", target = "amount")
    RespOrderSumDto toOrderSummary(OrderSummaryEntity entity);

    RespPaymentInfoDto toPaymentInfo(PaymentInfoEntity entity);

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "cashierId", ignore = true)
    PaymentConfirmation toConfirmationEntity(ReqPayConfirmDto entity);
}
