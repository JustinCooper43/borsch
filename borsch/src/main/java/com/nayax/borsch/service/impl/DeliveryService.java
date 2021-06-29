package com.nayax.borsch.service.impl;

import com.nayax.borsch.mapper.OrderItemMapper;
import com.nayax.borsch.mapper.OrderProcessingMapper;
import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.response.RespOrderDeliveryDto;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.model.entity.order.OrderDeliveryEntity;
import com.nayax.borsch.model.entity.order.OrderEntity;
import com.nayax.borsch.model.entity.order.OrderSumTimerEntity;
import com.nayax.borsch.repository.impl.DeliverySummaryRepository;
import com.nayax.borsch.utility.OrderEntityHashNoOrderUserTime;
import com.nayax.borsch.utility.PageDtoBuilder;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DeliveryService {
    @Autowired
    DeliverySummaryRepository deliverySummaryRepository;

    public ResponseDto<PageDto<RespOrderDeliveryDto>> getPagedDeliveryInfo(int page, int pageSize, LocalDateTime date) {
        if (date == null) {
            date = LocalDateTime.now();
        }
        OrderSumTimerEntity timerEntity = deliverySummaryRepository.getTimerBeforeDate(date);
        List<OrderEntity> orderList = deliverySummaryRepository.getByOrderSummaryId(timerEntity.getId());
        Map<GeneralPriceItemEntity, Integer> drinks = new HashMap<>();
        Map<OrderEntityHashNoOrderUserTime, Integer> orders = new HashMap<>();
        for (OrderEntity i : orderList) {
            OrderEntityHashNoOrderUserTime e = Mappers.getMapper(OrderItemMapper.class).transferEntity(i);
            if (drinks.containsKey(e.getDrink())) {
                drinks.replace(e.getDrink(), drinks.get(e.getDrink()) + e.getQuantity());
            } else {
                drinks.put(e.getDrink(), e.getQuantity());
            }
            orders.putIfAbsent(e, 0);
            orders.replace(e, orders.get(e) + e.getQuantity());
        }
        List<OrderDeliveryEntity> responseEntity = new ArrayList<>();
        for (OrderEntityHashNoOrderUserTime e : orders.keySet()) {
            OrderDeliveryEntity composed = new OrderDeliveryEntity();
            composed.setOrder(e);
            composed.setQuantity(orders.get(e));
            composed.setOrderDate(e.getCreationTime());
            responseEntity.add(composed);
        }
        for (GeneralPriceItemEntity e : drinks.keySet()) {
            OrderEntity drink = new OrderEntity();
            drink.setDrink(e);
            //hacks to avoid NPE
            drink.setOrderId(-1L);
            drink.setCreationTime(LocalDateTime.MIN);
            drink.setRemark(e);
            drink.setAdditions(List.of(e));
            drink.setQuantity(-1);
            drink.setCut(false);
            drink.setOrderSummaryId(-1L);
            drink.setDish(new ShawarmaItemEntity());
            //TODO find better solution for latter 8 lines
            OrderDeliveryEntity composed = new OrderDeliveryEntity();
            composed.setOrder(drink);
            composed.setQuantity(drinks.get(e));
            //setting the date from method params !!!
            composed.setOrderDate(date);
            responseEntity.add(composed);
        }
        int totalElements = responseEntity.size();
        int pageFrom = (page - 1) * pageSize;
        int pageTo = Math.min(pageFrom + pageSize, totalElements);
        int totalPages = totalElements % pageSize == 0 ?
                totalElements / pageSize :
                totalElements / pageSize + 1;
        responseEntity = responseEntity.subList(pageFrom, pageTo);
        List<RespOrderDeliveryDto> respList = responseEntity.stream()
                .map(Mappers.getMapper(OrderProcessingMapper.class)::toOrderDelivery)
                .collect(Collectors.toList());
        PageDto<RespOrderDeliveryDto> responsePage = new PageDtoBuilder<RespOrderDeliveryDto>()
                .page(respList)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .elementsPerPage(pageSize)
                .currentPageNum(page)
                .build();
        return new ResponseDto<>(responsePage);
    }
}
