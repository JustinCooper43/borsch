package com.nayax.borsch.service.impl;


import com.nayax.borsch.mapper.SimpleItemsMapper;
import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimpleItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimpleItemUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import com.nayax.borsch.model.entity.PageEntity;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.repository.impl.AdditionsRepository;
import com.nayax.borsch.repository.impl.TablesType;
import com.nayax.borsch.validation.config.PageIdValidationConfig;
import com.nayax.borsch.validation.config.RemarkValidationConfig;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.config.ConfigRepo;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RemarksService {

    @Autowired
    AdditionsRepository additionsRepository;

    public ResponseDto<RespSimpleItemDto> addRemarkItem(ReqSimpleItemAddDto dto, TablesType tableType) {

        List<ErrorDto> errors = RemarkValidationConfig.getValidatorRemark().validate(dto, ValidationAction.REMARK_ADD);
        if (errors.size() > 0) {
            return new ResponseDto<>(errors);
        }
        GeneralPriceItemEntity entity = additionsRepository.add(Mappers.getMapper(SimpleItemsMapper.class).toGeneralPriceItemEntity(dto), tableType);
        RespSimpleItemDto respDto = Mappers.getMapper(SimpleItemsMapper.class).toItemDto(entity);
        return new ResponseDto<>(respDto);
    }

    public ResponseDto<RespSimpleItemDto> editRemarkItem(ReqSimpleItemUpDto dto, TablesType tableType) {

        List<ErrorDto> errors = ConfigRepo.getValidatorRemark().validate(dto, ValidationAction.REMARK_UPDATE);
        if (errors.size() > 0) {
            return new ResponseDto<>(errors);
        }

        GeneralPriceItemEntity entity = additionsRepository.update(Mappers.getMapper(SimpleItemsMapper.class).toGeneralPriceItemEntity(dto), tableType);
        RespSimpleItemDto respDto = Mappers.getMapper(SimpleItemsMapper.class).toItemDto(entity);
        return new ResponseDto<>(respDto);
    }

    public ResponseDto<PageDto<RespSimpleItemDto>> getRemarkItemPage(int page, int pageSize, TablesType tableType) {
        List<ErrorDto> errorsPage = PageIdValidationConfig.getValidatorPageId().validate(page, ValidationAction.REMARK_GETALL);
//        if (errorsPage.size() > 0) {
//            return new ResponseDto<>(errorsPage);
//        }
        errorsPage.addAll(PageIdValidationConfig.getValidatorPageId().validate(pageSize, ValidationAction.REMARK_GETALL));
        if (errorsPage.size() > 0) {
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
        PageDto<RespSimpleItemDto> listRespDto = Mappers.getMapper(SimpleItemsMapper.class).toPageDto(listEntity);
        return new ResponseDto<>(listRespDto);
    }

    public ResponseDto<RespSimpleItemDto> getRemarkItemById(Long id, TablesType nameTable) {

//        Optional<GeneralPriceItemEntity> entity =  additionsRepository.findById(id, nameTable);
//        ResponseDto<RespSimpleItemDto> response = new ResponseDto<>();
//        if (entity.isPresent()) {
//            response.setData(Mappers.getMapper(SimpleItemsMapper.class).toItemDto(entity.get()));
//        } else {
//            ErrorDto e = new ErrorDto();
//            e.setMessage("Item is not found by id " + id);
//            response.setErrors(List.of(e));
//        }
        return null;
    }

    public ResponseDto<RespSimpleItemDto> delRemarkItemById(Long id, TablesType nameTable) {
        List<ErrorDto> errors = PageIdValidationConfig.getValidatorPageId().validate(id, ValidationAction.REMARK_DEL);
        if (errors.size() > 0) {
            return new ResponseDto<>(errors);
        }
        List<ErrorDto> errorsFromRepo = ConfigRepo.getValidatorRemark().validate(id, ValidationAction.REMARK_DEL);
        if (errorsFromRepo.size() > 0) {
            return new ResponseDto<>(errorsFromRepo);
        }
        Optional<GeneralPriceItemEntity> entity = additionsRepository.delete(id, nameTable);
        return new ResponseDto<>(Mappers.getMapper(SimpleItemsMapper.class).toItemDto(entity.get()));
    }

}
