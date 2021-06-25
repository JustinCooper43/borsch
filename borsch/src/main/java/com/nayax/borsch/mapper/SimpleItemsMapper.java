package com.nayax.borsch.mapper;

import com.nayax.borsch.model.dto.assortment.request.ReqSimpleItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimpleItemUpDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespPriceItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.entity.assortment.AssortmentRespEntity;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.assortment.PriceItemType;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper
public interface SimpleItemsMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    GeneralPriceItemEntity toGeneralPriceItemEntity(ReqSimplePriceItemAddDto dto);

    @Mapping(target = "active", ignore = true)
    GeneralPriceItemEntity toGeneralPriceItemEntity(ReqSimplePriceItemUpDto dto);

    RespSimplePriceItemDto toPriceItemDto(GeneralPriceItemEntity entity);

    RespPriceItemDto toTypedMenuList(PriceItemType entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "active", ignore = true)
    GeneralPriceItemEntity toGeneralPriceItemEntity(ReqSimpleItemAddDto dto);

    @Mapping(target = "price", ignore = true)
    @Mapping(target = "active", ignore = true)
    GeneralPriceItemEntity toGeneralPriceItemEntity(ReqSimpleItemUpDto dto);

    RespSimpleItemDto toItemDto(GeneralPriceItemEntity entity);

    default java.sql.Date ldtToDate(LocalDateTime localDateTime) {
        return new java.sql.Date(java.util.Date.from(localDateTime.atZone(ZoneId.of("GMT")).toInstant()).getTime());
    }


    AssortmentRespEntity toAssortmentRespEntity(ShawarmaItemEntity entity);
}
