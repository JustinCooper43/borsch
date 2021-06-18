package com.nayax.borsch.mapper;

import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SimpleItemsMapper {
    RespSimplePriceItemDto toPriceItemDto(GeneralPriceItemEntity entity);

    RespSimpleItemDto toItemDto(GeneralPriceItemEntity entity);
}
