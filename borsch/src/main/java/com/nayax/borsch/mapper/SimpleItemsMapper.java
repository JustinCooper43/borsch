package com.nayax.borsch.mapper;

import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimpleItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimpleItemUpDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespPriceItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.entity.PageEntity;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.assortment.PriceItemType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SimpleItemsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    GeneralPriceItemEntity toGeneralPriceItemEntity(ReqSimplePriceItemAddDto dto);

    @Mapping(target = "active", ignore = true)
    GeneralPriceItemEntity toGeneralPriceItemEntity(ReqSimplePriceItemUpDto dto);

    RespPriceItemDto toTypedMenuList(PriceItemType entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "active", ignore = true)
    GeneralPriceItemEntity toGeneralPriceItemEntity(ReqSimpleItemAddDto dto);

    @Mapping(target = "price", ignore = true)
    @Mapping(target = "active", ignore = true)
    GeneralPriceItemEntity toGeneralPriceItemEntity(ReqSimpleItemUpDto dto);

    PageDto<RespSimplePriceItemDto> toPagePriceDto(PageEntity<GeneralPriceItemEntity> entityPage);

    List<RespSimplePriceItemDto> toListPagePriceItemDto(List<GeneralPriceItemEntity> entityList);

    RespSimplePriceItemDto toPriceItemDto(GeneralPriceItemEntity entity);

    PageDto<RespSimpleItemDto> toPageDto(PageEntity<GeneralPriceItemEntity> entityPage);

    RespSimpleItemDto toItemDto(GeneralPriceItemEntity entity);

    default LocalDateTime dateToLdt(LocalDate date) {
        return date.atStartOfDay();
    }

    default LocalDate ldtToDate(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.toLocalDate() : null;
    }
}
