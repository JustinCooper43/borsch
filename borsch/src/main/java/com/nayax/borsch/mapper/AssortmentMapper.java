package com.nayax.borsch.mapper;

import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.assortment.request.ReqAssortmentUpDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespAssortmentDto;
import com.nayax.borsch.model.dto.assortment.response.RespAssortmentItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.entity.PageEntity;
import com.nayax.borsch.model.entity.assortment.AssortmentRespEntity;
import com.nayax.borsch.model.entity.assortment.AssortmentUpEntity;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = SimpleItemsMapper.class)
public interface AssortmentMapper {
    @Mapping(source = "dish", target = "dish")
    @Mapping(source = "additions", target = "additionsId")
    @Mapping(source = "remarks", target = "remarksId")
    AssortmentUpEntity toAssortmentUpdateEntity(ReqAssortmentUpDto dto);

    RespAssortmentItemDto toResponseAssortmentItemDto(ShawarmaItemEntity entity);

    RespAssortmentDto assortmentEntityToDto(AssortmentRespEntity entity);

    List<RespSimpleItemDto> fromEntityToRespDto(List<GeneralPriceItemEntity> itemEntities);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "halfAble", ignore = true)
    ShawarmaItemEntity toShawarmaItemEntity(ReqSimplePriceItemAddDto dto);

    RespSimplePriceItemDto toRespSimplePriceItemDto(ShawarmaItemEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "halfAble", ignore = true)
    ShawarmaItemEntity toShawarmaItemEntity(ReqSimplePriceItemUpDto dto);

    List<RespSimplePriceItemDto> toListPagePriceItemDto(List<ShawarmaItemEntity> entityList);
    PageDto<RespSimplePriceItemDto> toPageDto(PageEntity<ShawarmaItemEntity> entityPage);

}