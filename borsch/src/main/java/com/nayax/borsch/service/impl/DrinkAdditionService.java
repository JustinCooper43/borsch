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
import com.nayax.borsch.validation.config.DrinkAdditionValidationConfig;
import com.nayax.borsch.validation.config.PageIdValidationConfig;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.config.ConfigRepo;
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
        List<ErrorDto> errorsAddition = DrinkAdditionValidationConfig.getValidatorDrinkAdd().validate(dto, ValidationAction.SIMPLE_PRICE_ITEM_ADD);
        if (errorsAddition.size() > 0) {
            return new ResponseDto<>(errorsAddition);
        }

        GeneralPriceItemEntity entity = additionsRepository.add(Mappers.getMapper(SimpleItemsMapper.class).toGeneralPriceItemEntity(dto), tableType);
        RespSimplePriceItemDto respDto = Mappers.getMapper(SimpleItemsMapper.class).toPriceItemDto(entity);
        return new ResponseDto<>(respDto);
    }

    public ResponseDto<RespSimplePriceItemDto> editGeneralItem(ReqSimplePriceItemUpDto dto, TablesType tableType) {
        if (tableType.equals(TablesType.ADDITION)) {
            List<ErrorDto> errors = ConfigRepo.getRepositoryValidator().validate(dto, ValidationAction.ADDITIONS_UPDATE);
            if (errors.size() > 0) {
                return new ResponseDto<>(errors);
            }
        } else if (tableType.equals(TablesType.EXTRAITEM)) {
            List<ErrorDto> errors = ConfigRepo.getRepositoryValidator().validate(dto, ValidationAction.DRINK_UPDATE);
            if (errors.size() > 0) {
                return new ResponseDto<>(errors);
            }
        }

        GeneralPriceItemEntity entity = additionsRepository.update(Mappers.getMapper(SimpleItemsMapper.class).toGeneralPriceItemEntity(dto), tableType);
        RespSimplePriceItemDto respDto = Mappers.getMapper(SimpleItemsMapper.class).toPriceItemDto(entity);
        return new ResponseDto<>(respDto);
    }

    public ResponseDto<PageDto<RespSimplePriceItemDto>> getGeneralItemPage(int page, int pageSize, TablesType tableType) {
        List<ErrorDto> errorsPage = PageIdValidationConfig.getValidatorPageId().validate(page, ValidationAction.PAGING);
        List<ErrorDto> errorsPageSize = PageIdValidationConfig.getValidatorPageId().validate(pageSize, ValidationAction.PAGING);
        if (errorsPage.size() > 0 || errorsPageSize.size() > 0) {
            return new ResponseDto<>(errorsPage);
        }
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

//    public ResponseDto<RespSimplePriceItemDto> getGeneralItemById(Long id, TablesType nameTable) {
//        Optional<GeneralPriceItemEntity> entity = additionsRepository.findById(id, nameTable);
//        ResponseDto<RespSimplePriceItemDto> response = new ResponseDto<>();
//        if (entity.isPresent()) {
//            response.setData(Mappers.getMapper(SimpleItemsMapper.class).toPriceItemDto(entity.get()));
//        } else {
//            ErrorDto e = new ErrorDto();
//            e.setMessage("Item is not found by id " + id);
//            response.setErrors(List.of(e));
//        }
//        return response;
//    }

    public ResponseDto<RespSimplePriceItemDto> delGeneralItemById(Long id, TablesType nameTable) {
        List<ErrorDto> errorsId = DrinkAdditionValidationConfig.getValidatorDrinkAdd().validate(id, ValidationAction.SIMPLE_PRICE_ITEM_DEL);
        if (errorsId.size() > 0) {
            return new ResponseDto<>(errorsId);
        }
        if (nameTable.equals(TablesType.ADDITION)) {
            List<ErrorDto> errors = ConfigRepo.getRepositoryValidator().validate(id, ValidationAction.ADDITIONS_DEL);
            if (errors.size() > 0) {
                return new ResponseDto<>(errors);
            }
        } else if (nameTable.equals(TablesType.EXTRAITEM)) {
            List<ErrorDto> errors = ConfigRepo.getRepositoryValidator().validate(id, ValidationAction.DRINK_DEL);
            if (errors.size() > 0) {
                return new ResponseDto<>(errors);
            }
        }
        Optional<GeneralPriceItemEntity> entity = additionsRepository.delete(id, nameTable);
        return new ResponseDto<>(Mappers.getMapper(SimpleItemsMapper.class).toPriceItemDto(entity.get()));
    }

}
