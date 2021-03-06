package com.nayax.borsch.mapper;

import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.dto.order.request.ReqOrderItemAddDto;
import com.nayax.borsch.model.dto.order.request.ReqOrderStartDto;
import com.nayax.borsch.model.dto.order.response.RespOrderItemDto;
import com.nayax.borsch.model.entity.PageEntity;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.model.entity.order.OrderEntity;
import com.nayax.borsch.model.entity.order.OrderStartEntity;
import com.nayax.borsch.utility.OrderEntityHashNoOrderUserTime;
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
    @Mapping(target = "orderSummaryId", ignore = true)
    @Mapping(target = "cost", ignore = true)
    OrderEntity toAddEntity(ReqOrderItemAddDto dto);

    List<GeneralPriceItemEntity> toItemList(List<Long> ids);

    @Mapping(target = "id")
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "active", ignore = true)
    GeneralPriceItemEntity toItemId(Long id);

    @Mapping(target = "orderDate", source = "creationTime")
    RespOrderItemDto toDto(OrderEntity entity);
    OrderEntityHashNoOrderUserTime transferEntity(OrderEntity entity);

    List<RespOrderItemDto> toListRespOrderDto(List<OrderEntity> listOrderEntity);

    @Mapping(target = "startTime", ignore = true)
    OrderStartEntity toOrderStart(ReqOrderStartDto dto);

    PageDto<RespOrderItemDto> toPageDto(PageEntity<OrderEntity> entityPage);

}
