package com.nayax.borsch.service.impl;

import com.nayax.borsch.mapper.OrderProcessingMapper;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.request.ReqPayConfirmDto;
import com.nayax.borsch.model.dto.order.response.RespPaymentInfoDto;
import com.nayax.borsch.model.entity.payment.PaymentConfirmation;
import com.nayax.borsch.model.entity.payment.PaymentInfoEntity;
import com.nayax.borsch.model.entity.user.ProfileEntity;
import com.nayax.borsch.repository.impl.OrderItemRepository;
import com.nayax.borsch.repository.impl.PaymentRepository;
import com.nayax.borsch.repository.impl.ProfileRepositoryImplementation;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    ProfileRepositoryImplementation profileRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    OrderItemRepository orderRepository;

    public ResponseDto<Boolean> confirmPayment(ReqPayConfirmDto dto) {
        PaymentConfirmation entity = Mappers.getMapper(OrderProcessingMapper.class).toConfirmationEntity(dto);
        Optional<List<Long>> orderToConfirm = orderRepository.getOrderIdByUser(entity);
        if (orderToConfirm.isPresent()) {
            entity.setOrderId(orderToConfirm.get().get(0));
            entity.setCashierId(orderToConfirm.get().get(1));
            return new ResponseDto<>(paymentRepository.confirmPayment(entity));
        }
        return new ResponseDto<>(false);
    }

    public ResponseDto<RespPaymentInfoDto> getPaymentStatusByUserId(Long userId) {
        ProfileEntity currentOrderCashierInfo = profileRepository.findById(
                profileRepository.getCurrentCashierUserIdByEmail("%")
                        .orElse(profileRepository.latestOrderSummaryCashier()))
                .orElse(new ProfileEntity());
        BigDecimal payed = paymentRepository.getPayedSumForLatestOrderSummary(userId);
        BigDecimal cost = paymentRepository.getTotalCostForLatestOrderSummary(userId);
        PaymentInfoEntity paymentInfo = new PaymentInfoEntity();
        paymentInfo.setCashier(currentOrderCashierInfo.getUserEntity());
        paymentInfo.setPaymentMethod(currentOrderCashierInfo.getCashierEntity());
        paymentInfo.setConfirmed(payed.compareTo(cost) > 0);
        paymentInfo.setCompleted(paymentInfo.isConfirmed());
        RespPaymentInfoDto responseDto = Mappers.getMapper(OrderProcessingMapper.class).toPaymentInfo(paymentInfo);
        return new ResponseDto<>(responseDto);
    }
}
