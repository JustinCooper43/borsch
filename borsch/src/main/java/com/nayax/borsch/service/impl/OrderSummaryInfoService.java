package com.nayax.borsch.service.impl;


import com.nayax.borsch.mapper.OrderProcessingMapper;
import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.response.RespOrderSumDto;
import com.nayax.borsch.model.dto.order.response.RespOrderSumInfoDto;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.order.OrderEntity;
import com.nayax.borsch.model.entity.order.OrderSummaryEntity;
import com.nayax.borsch.model.entity.payment.OrderItemTotalCostInfo;
import com.nayax.borsch.repository.impl.OrderItemRepo;
import com.nayax.borsch.repository.impl.PaymentRepository;
import com.nayax.borsch.repository.impl.RepositoryOrderSummaryImpl;
import com.nayax.borsch.utility.PageDtoBuilder;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderSummaryInfoService {

    @Autowired
    OrderItemRepo orderItemRepo;

    @Autowired
    RepositoryOrderSummaryImpl orderSummary;
    @Autowired
    private PaymentRepository paymentRepository;

    public ResponseDto<RespOrderSumInfoDto> getOrderSumInfo(LocalDateTime dateTime) {

        Map<Long, BigDecimal> map = orderItemRepo.getPayedSumById(dateTime);
        //get all dish, drink, additions
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


    public ResponseDto<PageDto<RespOrderSumDto>> getSummaryOrder(LocalDate date, int page, int pageSize) {
        List<OrderSummaryEntity> orderSum = orderSummary.findAll(date);
        Map<Long, BigDecimal> pay = paymentRepository.getSumPaymentByUser(date);

        for (OrderSummaryEntity order : orderSum) {
            BigDecimal sum = new BigDecimal("0");
            for (OrderEntity entity : order.getOrders()) {
                sum = sum.add(entity.getDish().getPrice()).add(entity.getDrink().getPrice());
                for (GeneralPriceItemEntity general : entity.getAdditions()) {
                    sum = sum.add(general.getPrice());
                }
            }
            order.setPayedSum(pay.get(order.getUser().getId()));
            order.setTotalOrdersCost(sum);
        }
        int totalElements = orderSum.size();
        int pageFrom = (page - 1) * pageSize;
        int pageTo = Math.min(pageFrom + pageSize, totalElements);
        int totalPages = totalElements % pageSize == 0 ?
                totalElements / pageSize :
                totalElements / pageSize + 1;
        orderSum = orderSum.subList(pageFrom, pageTo);

        List<RespOrderSumDto> listDto = orderSum.stream()
                .map(Mappers.getMapper(OrderProcessingMapper.class)::toOrderSummary)
                .collect(Collectors.toList());

        PageDto<RespOrderSumDto> responsePage = new PageDtoBuilder<RespOrderSumDto>()
                .page(listDto)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .elementsPerPage(pageSize)
                .currentPageNum(page)
                .build();
        return new ResponseDto<>(responsePage);
    }
}
