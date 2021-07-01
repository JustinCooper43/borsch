package com.nayax.borsch.service.impl;


import com.nayax.borsch.mapper.AssortmentMapper;
import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.entity.PageEntity;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.repository.impl.RepositoryShawarmaTypeImpl;
import com.nayax.borsch.validation.componentimpl.SimpleValidatorComponent;
import com.nayax.borsch.validation.config.ConfigRepo;
import com.nayax.borsch.validation.config.DishValidationConfig;
import com.nayax.borsch.validation.config.DrinkAdditionValidationConfig;
import com.nayax.borsch.validation.config.PageIdValidationConfig;
import com.nayax.borsch.validation.enums.ValidationAction;
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
        List<ErrorDto> errors = DishValidationConfig.getValidator().validate(dto, ValidationAction.DISH_ADD);
        if (errors.size() > 0) {
            return new ResponseDto<>(errors);
        }
        ShawarmaItemEntity entity = repositoryShawarmaType.add(Mappers.getMapper(AssortmentMapper.class).toShawarmaItemEntity(dto));
        RespSimplePriceItemDto respDto = Mappers.getMapper(AssortmentMapper.class).toRespSimplePriceItemDto(entity);
        return new ResponseDto<>(respDto);
    }

    public ResponseDto<RespSimplePriceItemDto> updateDish(ReqSimplePriceItemUpDto dto) {
        ShawarmaItemEntity entity = Mappers.getMapper(AssortmentMapper.class).toShawarmaItemEntity(dto);
        List<ErrorDto> errors = DishValidationConfig.getValidator().validate(dto, ValidationAction.DISH_UPDATE);
        errors.addAll(ConfigRepo.getValidatorRemark().validate(dto.getId(),ValidationAction.DISH_DELETE));
        if (errors.size() > 0) {
            return new ResponseDto<>(errors);
        }
        ShawarmaItemEntity res = repositoryShawarmaType.update(entity);
        RespSimplePriceItemDto respDto = Mappers.getMapper(AssortmentMapper.class).toRespSimplePriceItemDto(res);
        return new ResponseDto<>(respDto);
    }

    public ResponseDto<RespSimplePriceItemDto> deleteDish(Long id) {

        List<ErrorDto> errorsId = PageIdValidationConfig.getValidatorPageId().validate(id, ValidationAction.DISH_DELETE);
        if (errorsId.size() > 0) {
            return new ResponseDto<>(errorsId);
        }
        List<ErrorDto> errorsFromRepo = ConfigRepo.getValidatorRemark().validate(id, ValidationAction.DISH_DELETE);
        if (errorsFromRepo.size() > 0) {
            return new ResponseDto<>(errorsFromRepo);
        }
        Optional<ShawarmaItemEntity> entity = repositoryShawarmaType.delete(id);
        ResponseDto<RespSimplePriceItemDto> response = new ResponseDto<>();
        if (entity.get().getId() != null) {
            response.setData(Mappers.getMapper(AssortmentMapper.class).toRespSimplePriceItemDto(entity.get()));
        } else {
            ErrorDto e = new ErrorDto();
            e.setMessage("Dish is not found by id " + id);
            response.setErrors(List.of(e));
        }
        return response;
    }

    public ResponseDto<PageDto<RespSimplePriceItemDto>> getDishByPage(int page, int pageSize) {


        PageEntity<ShawarmaItemEntity> listEntity = repositoryShawarmaType.findAll(page, pageSize);

        listEntity.setPage(page);
        listEntity.setPageSize(pageSize);

        Integer totalElements = listEntity.getTotalElements();
        int totalPages = totalElements % pageSize == 0 ?
                totalElements / pageSize :
                totalElements / pageSize + 1;
        listEntity.setTotalPages(totalPages);

        PageDto<RespSimplePriceItemDto> pageDto = Mappers.getMapper(AssortmentMapper.class).toPageDto(listEntity);
        return new ResponseDto<>(pageDto);
    }
}
