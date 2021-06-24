package com.nayax.borsch.service.impl;


import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.response.RespOrderSumInfoDto;
import com.nayax.borsch.model.entity.payment.OrderItemTotalCostInfo;
import com.nayax.borsch.repository.impl.OrderItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderSummaryInfoService {

    @Autowired
    OrderItemRepo orderItemRepo;

    public ResponseDto<RespOrderSumInfoDto> getOrderSumInfo(LocalDateTime dateTime) {

        Map<Long, BigDecimal> map = orderItemRepo.getPayedSumById(dateTime);
        List<OrderItemTotalCostInfo> listResult = orderItemRepo.getOrderInfo(dateTime);
        BigDecimal totalCost = calculateTotalCost(listResult);
        BigDecimal confirm = confirmedPayedSum(map, listResult);
        BigDecimal unConfirm = unConfirmedPayedSum(confirm, totalCost);
        RespOrderSumInfoDto dto = new RespOrderSumInfoDto();
        dto.setPayAmount(totalCost);
        dto.setPayConfirmed(confirm);
        dto.setPayCompleted(confirm);
        dto.setPayUnconfirmed(unConfirm);
        return new ResponseDto<>(dto);
    }

    private BigDecimal calculateTotalCost(List<OrderItemTotalCostInfo> listOrdersCost) {

        BigDecimal totalCost = new BigDecimal("0");

        for (OrderItemTotalCostInfo orderItem : listOrdersCost) {
            BigDecimal totalCostOrder;
            totalCostOrder = orderItem.getAdditionCost()
                    .add(orderItem.getDishCost())
                    .add(orderItem.getDrinkCost());
            BigDecimal quantity = new BigDecimal(orderItem.getQuantity());
            totalCostOrder = totalCostOrder.multiply(quantity);

            orderItem.setTotalCost(totalCostOrder);

            totalCost = totalCost.add(totalCostOrder);
        }
        return totalCost;
    }

    private BigDecimal confirmedPayedSum(Map<Long, BigDecimal> mapPaymentUser, List<OrderItemTotalCostInfo> listOrdersCost) {

        BigDecimal confirm = new BigDecimal("0");

        Map<Long, BigDecimal> mapCostForUser = new HashMap<>();

        for (OrderItemTotalCostInfo var : listOrdersCost) {
            mapCostForUser.merge(var.getUserId(), var.getTotalCost(), BigDecimal::add);
        }
        for (Long userId : mapPaymentUser.keySet()) {
            BigDecimal totalCostForUser = mapCostForUser.get(userId);
            BigDecimal totalPayedForUser = mapPaymentUser.get(userId);

            confirm = confirm.add(totalCostForUser.compareTo(totalPayedForUser) < 0 ? totalCostForUser : totalPayedForUser);
        }
        return confirm;
    }

    private BigDecimal unConfirmedPayedSum(BigDecimal confirm, BigDecimal totalCost) {
        return totalCost.subtract(confirm);
    }
}
