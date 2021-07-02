package com.nayax.borsch.service.impl;


import com.nayax.borsch.mapper.OrderItemMapper;
import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.request.ReqOrderItemAddDto;
import com.nayax.borsch.model.dto.order.response.RespOrderItemDto;
import com.nayax.borsch.model.entity.PageEntity;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.order.OrderEntity;
import com.nayax.borsch.repository.impl.OrderItemRepo;
import com.nayax.borsch.repository.impl.OrderItemRepository;
import com.nayax.borsch.repository.impl.RepositoryOrderSummaryImpl;
import com.nayax.borsch.utility.PageDtoBuilder;
import com.nayax.borsch.utility.enums.ErrorStatus;
import com.nayax.borsch.validation.config.*;
import com.nayax.borsch.validation.enums.ValidationAction;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class OrderItemService {

    @Autowired
    OrderItemRepo orderItemRepo;

    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    RepositoryOrderSummaryImpl orderSummaryRepository;


    public ResponseDto<List<RespOrderItemDto>> getListOrder(Long userId, String dateString) {
        List<ErrorDto> validationErrors = DateTimeValidatorConfig.getDateTimeValidator().validate(dateString, ValidationAction.DATE);
        validationErrors.addAll(DrinkAdditionValidationConfig.getValidatorDrinkAdd().validate(userId, ValidationAction.USER_VERIFY_ID));
        if (validationErrors.size() > 0) {
            return new ResponseDto<>(validationErrors);
        }
        validationErrors.addAll(ConfigRepo.getRepositoryValidator().validate(userId, ValidationAction.USER_VERIFY_ID));
        if (validationErrors.size() > 0) {
            return new ResponseDto<>(validationErrors);
        }

        LocalDate date = LocalDate.parse(dateString);
        List<OrderEntity> listOrders = orderItemRepo.getListOrders(userId, date);
        Set<Long> setOrderId = new HashSet<>();
        for (OrderEntity entity : listOrders) {
            setOrderId.add(entity.getOrderId());
        }
        if (!setOrderId.isEmpty()) {
            Map<Long, List<GeneralPriceItemEntity>> mapUserIdAddition = orderItemRepo.getMapAdditions(setOrderId, date);

            for (OrderEntity var : listOrders) {
                var.setAdditions(mapUserIdAddition.get(var.getOrderId()));
            }

            setCostOrderItem(listOrders);
        }
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

    public ResponseDto<RespOrderItemDto> addOrder(ReqOrderItemAddDto dto) {
        List<ErrorDto> validationErrors = OrderItemValidationConfig.getOrderItemValidator().validate(dto, ValidationAction.ORDER_ITEM_ADD);
        if (validationErrors.size() > 0) {
            return new ResponseDto<>(validationErrors);
        }

        validationErrors = ConfigRepo.getRepositoryValidator().validate(dto.getUserId(), ValidationAction.USER_VERIFY_ID);
        validationErrors.addAll(ConfigRepo.getRepositoryValidator().validate(dto.getDish(), ValidationAction.DISH_VERIFY_ID));
        validationErrors.addAll(ConfigRepo.getRepositoryValidator().validate(dto.getDrink(), ValidationAction.DRINK_VERIFY_ID));
        validationErrors.addAll(ConfigRepo.getRepositoryValidator().validate(dto.getRemark(), ValidationAction.REMARK_VERIFY_ID));
        for (Long additionId : dto.getAdditions()) {
            validationErrors.addAll(ConfigRepo.getRepositoryValidator().validate(additionId, ValidationAction.ADDITIONS_VERIFY_ID));
        }
        if (validationErrors.size() > 0) {
            return new ResponseDto<>(validationErrors);
        }

        OrderEntity entity = Mappers.getMapper(OrderItemMapper.class).toAddEntity(dto);
        Optional<Long> latestOrderSummaryId = orderSummaryRepository.getLatestOrderSummaryId();
        if (latestOrderSummaryId.isPresent()) {
            entity.setOrderSummaryId(latestOrderSummaryId.get());
        } else {
            return new ResponseDto<RespOrderItemDto>(List.of(new ErrorDto("No currently opened order"))).setStatus("422");
        }

        OrderEntity order = orderItemRepository.add(entity);
        RespOrderItemDto orderDto = Mappers.getMapper(OrderItemMapper.class).toDto(order);
        return new ResponseDto<>(orderDto);
    }

    public ResponseDto<RespOrderItemDto> deleteOrder(Long id) {
        List<ErrorDto> validationErrors = (DrinkAdditionValidationConfig.getValidatorDrinkAdd().validate(id, ValidationAction.ORDER_VERIFY_ID));
        if (validationErrors.size() > 0) {
            return new ResponseDto<>(validationErrors);
        }
        validationErrors.addAll(ConfigRepo.getRepositoryValidator().validate(id, ValidationAction.ORDER_VERIFY_ID));
        if (validationErrors.size() > 0) {
            return new ResponseDto<>(validationErrors);
        }

        OrderEntity order = orderItemRepository.deleteById(id);
        RespOrderItemDto orderDto = Mappers.getMapper(OrderItemMapper.class).toDto(order);
        return new ResponseDto<>(orderDto);
    }

    public ResponseDto<PageDto<RespOrderItemDto>> getPagedHistory(Long userId, int page, int pageSize) {
        List<ErrorDto> validationErrors = DrinkAdditionValidationConfig.getValidatorDrinkAdd().validate(userId, ValidationAction.USER_VERIFY_ID);
        validationErrors.addAll(PageIdValidationConfig.getValidatorPageId().validate(page, ValidationAction.PAGING));
        validationErrors.addAll(PageIdValidationConfig.getValidatorPageId().validate(pageSize, ValidationAction.PAGING));
        if (validationErrors.size() > 0) {
            return new ResponseDto<>(validationErrors);
        }
        validationErrors.addAll(ConfigRepo.getRepositoryValidator().validate(userId, ValidationAction.USER_VERIFY_ID));
        if (validationErrors.size() > 0) {
            return new ResponseDto<>(validationErrors);
        }
        int totalPages = PageDtoBuilder.getTotalPages(pageSize, orderItemRepository.getOrderCountByUserId(userId));
        if (totalPages < page) {
            validationErrors.add(new ErrorDto("Incorrect number page", "page"));
            return new ResponseDto<>(validationErrors);
        }

        PageEntity<OrderEntity> listEntity = orderItemRepo.getPagedHistory(userId, page, pageSize);
        if (listEntity.getResponseList().get(0).getOrderId() == null) {
            ResponseDto<PageDto<RespOrderItemDto>> response = new ResponseDto<>();
            return response.setStatus(ErrorStatus.NOT_FOUND.statusName);
        }
        listEntity.setPage(page);
        listEntity.setPageSize(pageSize);
        listEntity.setTotalPages(totalPages);
        PageDto<RespOrderItemDto> listDto = Mappers.getMapper(OrderItemMapper.class).toPageDto(listEntity);
        return new ResponseDto<>(listDto);
    }
}
