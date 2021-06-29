package com.nayax.borsch.service.impl;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.repository.impl.RepositoryOrderSummaryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TimerService {
    @Autowired
    RepositoryOrderSummaryImpl orderSummaryRepository;

    public ResponseDto<Boolean> startOrder(dto) {
        //map
        return new ResponseDto<>(orderSummaryRepository.startNewSummaryOrder());
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
}
