package com.nayax.borsch.service.impl;


import com.nayax.borsch.mapper.AssortmentMapper;
import com.nayax.borsch.mapper.SimpleItemsMapper;
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
import com.nayax.borsch.utility.enums.ErrorStatus;
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
            return new ResponseDto<RespSimplePriceItemDto>(errors).setStatus(ErrorStatus.UNAUTHORIZED.statusName);
        }
        ShawarmaItemEntity entity = repositoryShawarmaType.add(Mappers.getMapper(AssortmentMapper.class).toShawarmaItemEntity(dto));
        RespSimplePriceItemDto respDto = Mappers.getMapper(AssortmentMapper.class).toRespSimplePriceItemDto(entity);
        return new ResponseDto<>(respDto);
    }

    public ResponseDto<RespSimplePriceItemDto> updateDish(ReqSimplePriceItemUpDto dto) {
        List<ErrorDto> errors = DishValidationConfig.getValidator().validate(dto, ValidationAction.DISH_UPDATE);
        if (errors.size() > 0) {
            return new ResponseDto<RespSimplePriceItemDto>(errors).setStatus(ErrorStatus.UNPROCESSIBLE.statusName);
        }
        errors.addAll(ConfigRepo.getRepositoryValidator().validate(dto.getId(),ValidationAction.DISH_DELETE));
        if (errors.size() > 0) {
            return new ResponseDto<RespSimplePriceItemDto>(errors).setStatus(ErrorStatus.UNPROCESSIBLE.statusName);
        }
        ResponseDto<RespSimplePriceItemDto> add = addDish(Mappers.getMapper(SimpleItemsMapper.class).toPriceItemAddDto(dto));
        boolean updated = repositoryShawarmaType.update(dto.getId(),add.getData().getId());
        ResponseDto<RespSimplePriceItemDto> deleted = deleteDish(dto.getId());
        if (deleted.getErrors() != null){
            return deleted;
        }
        if (updated){
            return add.setStatus(ErrorStatus.OK.statusName);
        }
        return add.setStatus(ErrorStatus.SERVER_ERROR.statusName);
    }

    public ResponseDto<RespSimplePriceItemDto> deleteDish(Long id) {

        List<ErrorDto> errorsId = PageIdValidationConfig.getValidatorPageId().validate(id, ValidationAction.DISH_DELETE);
        if (errorsId.size() > 0) {
            return new ResponseDto<RespSimplePriceItemDto>(errorsId).setStatus(ErrorStatus.UNPROCESSIBLE.statusName);
        }
        List<ErrorDto> errorsFromRepo = ConfigRepo.getRepositoryValidator().validate(id, ValidationAction.DISH_DELETE);
        if (errorsFromRepo.size() > 0) {
            return new ResponseDto<RespSimplePriceItemDto>(errorsFromRepo).setStatus(ErrorStatus.UNPROCESSIBLE.statusName);
        }
        Optional<ShawarmaItemEntity> entity = repositoryShawarmaType.delete(id);
        ResponseDto<RespSimplePriceItemDto> response = new ResponseDto<>();
        if (entity.get().getId() != null) {
            response.setData(Mappers.getMapper(AssortmentMapper.class).toRespSimplePriceItemDto(entity.get()));
        } else {
            ErrorDto e = new ErrorDto();
            e.setMessage("Dish is not found by id " + id);
            response.setErrors(List.of(e));
            return response.setStatus(ErrorStatus.NOT_FOUND.statusName);
        }
        return response.setStatus(ErrorStatus.OK.statusName);
    }

    public ResponseDto<PageDto<RespSimplePriceItemDto>> getDishByPage(int page, int pageSize) {
        List<ErrorDto> errorsPage = PageIdValidationConfig.getValidatorPageId().validate(page, ValidationAction.PAGING);
        errorsPage.addAll(PageIdValidationConfig.getValidatorPageId().validate(pageSize, ValidationAction.PAGING));
        if (errorsPage.size() > 0) {
            return new ResponseDto<PageDto<RespSimplePriceItemDto>>(errorsPage).setStatus(ErrorStatus.UNPROCESSIBLE.statusName);
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
