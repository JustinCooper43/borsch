package com.nayax.borsch.mapper;

import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.dto.order.request.ReqOrderItemAddDto;
import com.nayax.borsch.model.dto.order.response.RespOrderItemDto;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.order.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = SimpleItemsMapper.class)
public interface OrderItemMapper {

    @Mapping(source = "dish", target = "dish.id")
    @Mapping(source = "additions", target = "additions")
    @Mapping(source = "drink", target = "drink.id")
    @Mapping(source = "remark", target = "remark.id")
    @Mapping(source = "orderDate", target = "creationTime")
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "orderSummaryId", ignore = true)
    OrderEntity toAddEntity(ReqOrderItemAddDto dto);

    List<GeneralPriceItemEntity> toItemList(List<Long> ids);

    @Mapping(target = "id")
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "price", ignore = true)
    GeneralPriceItemEntity toItemId(Long id);

    RespOrderItemDto toDto(OrderEntity entity);
}
