package com.nayax.borsch.service.impl;

import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.response.RespOrderDeliveryDto;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.order.OrderEntity;
import com.nayax.borsch.model.entity.order.OrderSumTimerEntity;
import com.nayax.borsch.repository.impl.DeliverySummaryRepository;
import com.nayax.borsch.utility.OrderEntityHashNoOrderUserTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeliveryService {
    @Autowired
    DeliverySummaryRepository deliverySummaryRepository;

    public ResponseDto<PageDto<RespOrderDeliveryDto>> getPagedDeliveryInfo(int page, int pageSize, LocalDateTime date) {
        OrderSumTimerEntity timerEntity = deliverySummaryRepository.getTimerBeforeDate(date);
        List<OrderEntity> orderList = deliverySummaryRepository.getByOrderSummaryId(timerEntity.getId());
        Map<GeneralPriceItemEntity, Integer> drinks = new HashMap<>();
        Map<OrderEntityHashNoOrderUserTime, Integer> orders = new HashMap<>();
        for (OrderEntity i : orderList) {
            OrderEntityHashNoOrderUserTime e = (OrderEntityHashNoOrderUserTime) i;
            if (drinks.containsKey(e.getDrink())) {
                drinks.put(e.getDrink(), drinks.get(e.getDrink()) + e.getQuantity());
            } else {
                drinks.put(e.getDrink(), e.getQuantity());
            }
            orders.putIfAbsent(e, 0);
            orders.put(e, orders.get(e) + e.getQuantity());
        }
        return null;
    }
}
