package com.nayax.borsch.mapper;

import com.nayax.borsch.model.dto.assortment.request.ReqAssortmentUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespAssortmentDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import com.nayax.borsch.model.entity.assortment.AssortmentRespEntity;
import com.nayax.borsch.model.entity.assortment.AssortmentUpEntity;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = SimpleItemsMapper.class)
public interface AssortmentMapper {
    @Mapping(source = "dish", target = "dish")
    @Mapping(source = "additions", target = "additionsId")
    @Mapping(source = "remarks", target = "remarksId")
    AssortmentUpEntity assortmentUpdateReqToEntity(ReqAssortmentUpDto dto);

    RespAssortmentDto assortmentEntityToDto(AssortmentRespEntity entity);

    List<RespSimpleItemDto> fromEntityToRespDto(List<GeneralPriceItemEntity> itemEntities);

}