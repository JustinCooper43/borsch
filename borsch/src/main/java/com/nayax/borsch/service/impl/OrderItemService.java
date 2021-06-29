package com.nayax.borsch.service.impl;


import com.nayax.borsch.mapper.OrderItemMapper;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.response.RespOrderItemDto;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.order.OrderEntity;
import com.nayax.borsch.repository.impl.OrderItemRepo;
import com.nayax.borsch.repository.impl.OrderItemRepository;
import com.nayax.borsch.repository.impl.RepositoryOrderSummaryImpl;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OrderItemService {

    @Autowired
    OrderItemRepo orderItemRepo;

    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    RepositoryOrderSummaryImpl orderSummaryRepository;

    public ResponseDto<List<RespOrderItemDto>> getListOrder(Long userId, LocalDateTime dateTime) {
        List<OrderEntity> listOrders = orderItemRepo.getListOrders(userId, dateTime);
        Set<Long> setOrderId = new HashSet<>();
        for (OrderEntity entity : listOrders) {
            setOrderId.add(entity.getOrderId());
        }

        Map<Long, List<GeneralPriceItemEntity>> mapUserIdAddition = orderItemRepo.getMapAdditions(setOrderId, dateTime);

        for (OrderEntity var : listOrders) {
            var.setAdditions(mapUserIdAddition.get(var.getOrderId()));
        }

        setCostOrderItem(listOrders);

        List<RespOrderItemDto> listDto = Mappers.getMapper(OrderItemMapper.class).toListRespOrderDto(listOrders);
        return new ResponseDto<>(listDto);
    }

    private void setCostOrderItem(List<OrderEntity> listOrders) {

        for (OrderEntity orderItem : listOrders) {
            BigDecimal dishPrice = orderItem.getDish().getPrice();
            BigDecimal drinkPrice = orderItem.getDrink().getPrice();
            BigDecimal additionPrice = new BigDecimal("0");
            for (GeneralPriceItemEntity additItem : orderItem.getAdditions()) {
                additionPrice = additionPrice.add(additItem.getPrice());
            }
            BigDecimal totalPrice = dishPrice.add(drinkPrice).add(additionPrice);
            BigDecimal quantity = new BigDecimal(orderItem.getQuantity());

            totalPrice = totalPrice.multiply(quantity);
            orderItem.setCost(totalPrice);
        }
    }
    public ResponseDto<RespOrderItemDto> addOrder(OrderEntity entity){
        entity.setOrderSummaryId(orderSummaryRepository.getLatestOrderSummaryId());
        OrderEntity order = orderItemRepository.add(entity);
        RespOrderItemDto orderDto = Mappers.getMapper(OrderItemMapper.class).toDto(order);
        return new ResponseDto<>(orderDto);
    }

    public ResponseDto<RespOrderItemDto> deleteOrder(Long id){
        OrderEntity order = orderItemRepository.deleteById(id);
        RespOrderItemDto orderDto = Mappers.getMapper(OrderItemMapper.class).toDto(order);
        return new ResponseDto<>(orderDto);
    }
}
