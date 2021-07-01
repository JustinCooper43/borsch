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
import com.nayax.borsch.utility.PageDtoBuilder;
import com.nayax.borsch.validation.config.ConfigRepo;
import com.nayax.borsch.validation.config.DishValidationConfig;
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
        List<ErrorDto> errors = DishValidationConfig.getValidator().validate(dto.getId(), ValidationAction.DISH_UPDATE);
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
        List<ErrorDto> errorsPage = PageIdValidationConfig.getValidatorPageId().validate(page, ValidationAction.PAGING);
        errorsPage.addAll(PageIdValidationConfig.getValidatorPageId().validate(pageSize, ValidationAction.PAGING));
        if (errorsPage.size() > 0) {
            return new ResponseDto<>(errorsPage);
        }

        PageEntity<ShawarmaItemEntity> listEntity = repositoryShawarmaType.findAll(page, pageSize);
        int totalPages = PageDtoBuilder.getTotalPages(pageSize, listEntity.getResponseList().size());
        if (totalPages < page) {
            errorsPage.add(new ErrorDto("Incorrect number page", "page"));
            return new ResponseDto<>(errorsPage);
        }

        listEntity.setPage(page);
        listEntity.setPageSize(pageSize);
        listEntity.setTotalPages(totalPages);

        PageDto<RespSimplePriceItemDto> pageDto = Mappers.getMapper(AssortmentMapper.class).toPageDto(listEntity);
        return new ResponseDto<>(pageDto);
    }
}
