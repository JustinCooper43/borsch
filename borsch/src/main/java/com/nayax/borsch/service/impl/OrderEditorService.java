package com.nayax.borsch.service.impl;

import com.nayax.borsch.mapper.AssortmentMapper;
import com.nayax.borsch.mapper.SimpleItemsMapper;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.dto.order.response.RespOrderItemDto;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.repository.impl.AdditionsRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderEditorService {


    @Autowired
    AdditionsRepository additionsRepository;

    public ResponseDto<List<RespSimplePriceItemDto>> additionalDropdown(Long dishId) {
        List<GeneralPriceItemEntity> listEntity = additionsRepository.findAllAdditionsById(dishId);
        List<RespSimplePriceItemDto> listRespDto = Mappers.getMapper(SimpleItemsMapper.class).toListPagePriceItemDto(listEntity);
        return new ResponseDto<>(listRespDto);
    }

}
