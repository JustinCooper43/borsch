package com.nayax.borsch.service.impl;

import com.nayax.borsch.mapper.OrderProcessingMapper;
import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.request.ReqPayConfirmDto;
import com.nayax.borsch.model.dto.order.response.RespPaymentInfoDto;
import com.nayax.borsch.model.entity.payment.PaymentConfirmation;
import com.nayax.borsch.model.entity.payment.PaymentInfoEntity;
import com.nayax.borsch.model.entity.user.ProfileEntity;
import com.nayax.borsch.repository.impl.OrderItemRepository;
import com.nayax.borsch.repository.impl.PaymentRepository;
import com.nayax.borsch.repository.impl.ProfileRepositoryImplementation;
import com.nayax.borsch.validation.config.ConfigRepo;
import com.nayax.borsch.validation.config.DrinkAdditionValidationConfig;
import com.nayax.borsch.validation.config.PaymentConfirmationValidationConfig;
import com.nayax.borsch.validation.enums.ValidationAction;
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
        List<ErrorDto> validationErrors = PaymentConfirmationValidationConfig.getPaymentValidator().validate(dto, ValidationAction.CONFIRM_PAYMENT);
        if (validationErrors.size() > 0) {
            return new ResponseDto<Boolean>(validationErrors).setStatus("422");
        }

        validationErrors.addAll(ConfigRepo.getRepositoryValidator().validate(dto.getUserId(), ValidationAction.USER_VERIFY_ID));
        validationErrors.addAll(ConfigRepo.getRepositoryValidator().validate(dto.getOrderDate(), ValidationAction.SUMM_ORDER_OPEN));
        if (validationErrors.size() > 0) {
            return new ResponseDto<Boolean>(validationErrors).setStatus("422");
        }

        PaymentConfirmation entity = Mappers.getMapper(OrderProcessingMapper.class).toConfirmationEntity(dto);
        Optional<List<Long>> orderToConfirm = orderRepository.getOrderIdByUser(entity);
        if (orderToConfirm.isPresent()) {
            entity.setOrderId(orderToConfirm.get().get(0));
            entity.setCashierId(orderToConfirm.get().get(1));
            boolean response = paymentRepository.confirmPayment(entity);
            return new ResponseDto<>(response).setStatus(response ? "200" : "422");
        }
        return new ResponseDto<>(false).setStatus("404");
    }

    public ResponseDto<RespPaymentInfoDto> getPaymentStatusByUserId(Long userId) {
        List<ErrorDto> validationErrors = DrinkAdditionValidationConfig.getValidatorDrinkAdd().validate(userId, ValidationAction.USER_VERIFY_ID);
        if (validationErrors.size() > 0) {
            return new ResponseDto<RespPaymentInfoDto>(validationErrors).setStatus("422");
        }

        validationErrors.addAll(ConfigRepo.getRepositoryValidator().validate(userId, ValidationAction.USER_VERIFY_ID));
        if (validationErrors.size() > 0) {
            return new ResponseDto<RespPaymentInfoDto>(validationErrors).setStatus("422");
        }

        ProfileEntity currentOrderCashierInfo = profileRepository.findById(
                profileRepository.getCurrentCashierUserIdByEmail("%")
                        .orElse(profileRepository.latestOrderSummaryCashier()))
                .orElse(new ProfileEntity());
        BigDecimal payed = paymentRepository.getPayedSumForLatestOrderSummary(userId).orElse(new BigDecimal("0"));
        BigDecimal cost = paymentRepository.getTotalCostForLatestOrderSummary(userId).orElse(new BigDecimal("0"));
        PaymentInfoEntity paymentInfo = new PaymentInfoEntity();
        paymentInfo.setCashier(currentOrderCashierInfo.getUserEntity());
        paymentInfo.setPaymentMethod(currentOrderCashierInfo.getCashierEntity());
        paymentInfo.setConfirmed(payed.compareTo(cost) >= 0);
        paymentInfo.setCompleted(paymentInfo.isConfirmed());
        RespPaymentInfoDto responseDto = Mappers.getMapper(OrderProcessingMapper.class).toPaymentInfo(paymentInfo);
        return new ResponseDto<>(responseDto).setStatus("200");
    }
}
