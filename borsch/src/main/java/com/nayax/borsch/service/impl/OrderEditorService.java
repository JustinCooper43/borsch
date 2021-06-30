package com.nayax.borsch.service.impl;

import com.nayax.borsch.mapper.AssortmentMapper;
import com.nayax.borsch.mapper.SimpleItemsMapper;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.response.RespAssortmentItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespPriceItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.assortment.PriceItemType;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.repository.impl.AdditionsRepository;
import com.nayax.borsch.repository.impl.PriceItemRepository;
import com.nayax.borsch.repository.impl.RepositoryShawarmaTypeImpl;
import com.nayax.borsch.repository.impl.TablesType;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderEditorService {
    @Autowired
    AdditionsRepository additionsRepository;
    @Autowired
    RepositoryShawarmaTypeImpl shawarmaType;
    @Autowired
    PriceItemRepository priceItemRepository;

    public ResponseDto<List<RespSimplePriceItemDto>> additionalDropdown(Long dishId) {
        List<GeneralPriceItemEntity> listEntity = additionsRepository.findAllAdditionsById(dishId);
        List<RespSimplePriceItemDto> listRespDto = Mappers.getMapper(SimpleItemsMapper.class).toListPagePriceItemDto(listEntity);
        return new ResponseDto<>(listRespDto);
    }

    public List<RespAssortmentItemDto> dish() {
        List<ShawarmaItemEntity> shawarmaTypeAll = shawarmaType.findAll();
        List<RespAssortmentItemDto> dto = shawarmaTypeAll.stream()
                .map(Mappers.getMapper(AssortmentMapper.class)::toResponseAssortmentItemDto)
                .collect(Collectors.toList());
        return dto;
    }

    public List<RespPriceItemDto> price() {
        List<PriceItemType> priceItemTypes = priceItemRepository.getAll();
        List<RespPriceItemDto> dto = priceItemTypes.stream()
                .map(Mappers.getMapper(SimpleItemsMapper.class)::toTypedMenuList)
                .collect(Collectors.toList());
        return dto;
    }

    public List<RespSimplePriceItemDto> drinks() {
        List<GeneralPriceItemEntity> entities = additionsRepository.findAll(TablesType.EXTRAITEM);
        List<RespSimplePriceItemDto> dto = entities.stream()
                .map(Mappers.getMapper(SimpleItemsMapper.class)::toPriceItemDto)
                .collect(Collectors.toList());
        return dto;
    }

    public List<RespSimpleItemDto> remarks() {
        List<GeneralPriceItemEntity> entities = additionsRepository.findAll(TablesType.REMARK);
        List<RespSimpleItemDto> dto = entities.stream()
                .map(Mappers.getMapper(SimpleItemsMapper.class)::toItemDto)
                .collect(Collectors.toList());
        return dto;
    }
}
