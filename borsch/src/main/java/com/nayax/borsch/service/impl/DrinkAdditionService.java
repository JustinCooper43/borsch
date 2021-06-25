package com.nayax.borsch.service.impl;


import com.nayax.borsch.mapper.SimpleItemsMapper;
import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.entity.PageEntity;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.repository.impl.AdditionsRepository;
import com.nayax.borsch.repository.impl.TablesType;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DrinkAdditionService {


    @Autowired
    AdditionsRepository additionsRepository;

    public ResponseDto<RespSimplePriceItemDto> addGeneralItem(ReqSimplePriceItemAddDto dto, TablesType tableType) {
        GeneralPriceItemEntity entity = additionsRepository.add(Mappers.getMapper(SimpleItemsMapper.class).toGeneralPriceItemEntity(dto), tableType);
        RespSimplePriceItemDto respDto = Mappers.getMapper(SimpleItemsMapper.class).toPriceItemDto(entity);
        return new ResponseDto<>(respDto);
    }

    public ResponseDto<RespSimplePriceItemDto> editGeneralItem(ReqSimplePriceItemUpDto dto, TablesType tableType) {
        GeneralPriceItemEntity entity = additionsRepository.update(Mappers.getMapper(SimpleItemsMapper.class).toGeneralPriceItemEntity(dto), tableType);
        RespSimplePriceItemDto respDto = Mappers.getMapper(SimpleItemsMapper.class).toPriceItemDto(entity);
        return new ResponseDto<>(respDto);
    }

    public ResponseDto<PageDto<RespSimplePriceItemDto>> getGeneralItemPage(int page, int pageSize, TablesType tableType) {

        PageEntity<GeneralPriceItemEntity> listEntity = additionsRepository.findAllPage(page, pageSize, tableType);
        listEntity.setPage(page);
        listEntity.setPageSize(pageSize);

        Integer totalElements = listEntity.getTotalElements();
        int totalPages = totalElements % pageSize == 0 ?
                totalElements / pageSize :
                totalElements / pageSize + 1;
        listEntity.setTotalPages(totalPages);
        PageDto<RespSimplePriceItemDto> listRespDto = Mappers.getMapper(SimpleItemsMapper.class).toPagePriceDto(listEntity);
        return new ResponseDto<>(listRespDto);
    }

    public ResponseDto<RespSimplePriceItemDto> getGeneralItemById(Long id,TablesType nameTable){
        Optional<GeneralPriceItemEntity> entity =  additionsRepository.findById(id, nameTable);
        ResponseDto<RespSimplePriceItemDto> response = new ResponseDto<>();
        if (entity.isPresent()) {
            response.setData(Mappers.getMapper(SimpleItemsMapper.class).toPriceItemDto(entity.get()));
        } else {
            ErrorDto e = new ErrorDto();
            e.setMessage("Item is not found by id " + id);
            response.setErrors(List.of(e));
        }
        return response;
    }

    public ResponseDto<RespSimplePriceItemDto> delGeneralItemById(Long id, TablesType nameTable){

        Optional<GeneralPriceItemEntity> entity =  additionsRepository.delete(id, nameTable);
        ResponseDto<RespSimplePriceItemDto> response = new ResponseDto<>();
        if (entity.isPresent()) {
            response.setData(Mappers.getMapper(SimpleItemsMapper.class).toPriceItemDto(entity.get()));
        } else {
            ErrorDto e = new ErrorDto();
            e.setMessage("Item is not found by id " + id);
            response.setErrors(List.of(e));
        }
        return response;
    }

}
