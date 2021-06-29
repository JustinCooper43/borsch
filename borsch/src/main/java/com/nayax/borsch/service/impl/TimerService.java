package com.nayax.borsch.service.impl;

import com.nayax.borsch.mapper.OrderItemMapper;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.enums.OrderSummaryStatus;
import com.nayax.borsch.model.dto.order.request.ReqOrderStartDto;
import com.nayax.borsch.model.dto.order.response.RespOrderStatusDto;
import com.nayax.borsch.model.entity.order.OrderStartEntity;
import com.nayax.borsch.repository.impl.RepositoryOrderSummaryImpl;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TimerService {
    @Autowired
    RepositoryOrderSummaryImpl orderSummaryRepository;

    public ResponseDto<Boolean> startOrder(ReqOrderStartDto dto) {
        OrderStartEntity entity = Mappers.getMapper(OrderItemMapper.class).toOrderStart(dto);
        entity.setStartTime(LocalDateTime.now());
        return new ResponseDto<>(orderSummaryRepository.startNewSummaryOrder(entity));
    }

    public ResponseDto<RespOrderStatusDto> getOrderStatus() {
        Optional<Long> orderSumId = orderSummaryRepository.getLatestOrderSummaryId();
        RespOrderStatusDto response = new RespOrderStatusDto();
        if (orderSumId.isEmpty()) {
            response.setStatus(OrderSummaryStatus.NOT_OPEN);
            return new ResponseDto<RespOrderStatusDto>(response);
        }
        response.setStatus(OrderSummaryStatus.OPEN);
        response.setEndTime(orderSummaryRepository.getSummaryOrderStatus(orderSumId.get()));
        return new ResponseDto<RespOrderStatusDto>(response);
    }

    public ResponseDto<Boolean> stopOrder() {
        return new ResponseDto<>(orderSummaryRepository.stopSummaryOrder(LocalDateTime.now()));
    }
}
