package com.nayax.borsch.service.impl;


import com.nayax.borsch.mapper.AssortmentMapper;
import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.repository.impl.RepositoryShawarmaTypeImpl;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShavarmaService {

    @Autowired
    RepositoryShawarmaTypeImpl repositoryShawarmaType;

    public ResponseDto<RespSimplePriceItemDto> addDish(ReqSimplePriceItemAddDto dto) {
        ShawarmaItemEntity entity = repositoryShawarmaType.add(Mappers.getMapper(AssortmentMapper.class).toShawarmaItemEntity(dto));
        RespSimplePriceItemDto respDto = Mappers.getMapper(AssortmentMapper.class).toRespSimplePriceItemDto(entity);
        return new ResponseDto<>(respDto);
    }

    public ResponseDto<RespSimplePriceItemDto> updateDish(ReqSimplePriceItemUpDto dto, Long id) {
        ShawarmaItemEntity entity = repositoryShawarmaType.update(Mappers.getMapper(AssortmentMapper.class).toShawarmaItemEntity(dto));
        RespSimplePriceItemDto respDto = Mappers.getMapper(AssortmentMapper.class).toRespSimplePriceItemDto(entity);
        return new ResponseDto<>(respDto);
    }

    public ResponseDto<RespSimplePriceItemDto> deleteDish(Long id) {

        Optional<ShawarmaItemEntity> entity = repositoryShawarmaType.delete(id);
        ResponseDto<RespSimplePriceItemDto> response = new ResponseDto<>();
        if (entity.isPresent()) {
            response.setData(Mappers.getMapper(AssortmentMapper.class).toRespSimplePriceItemDto(entity.get()));
        } else {
            ErrorDto e = new ErrorDto();
            e.setMessage("Dish is not found by id " + id);
            response.setErrors(List.of(e));
        }
        return response;
    }

    //TODO get dish by page
}
