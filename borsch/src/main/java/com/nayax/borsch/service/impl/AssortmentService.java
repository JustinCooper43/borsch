package com.nayax.borsch.service.impl;

import com.nayax.borsch.mapper.AssortmentMapper;
import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqAssortmentUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespAssortmentDto;
import com.nayax.borsch.model.entity.assortment.AssortmentRespEntity;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.repository.impl.RepositoryAssortmentImpl;
import com.nayax.borsch.repository.impl.RepositoryShawarmaTypeImpl;
import com.nayax.borsch.utility.PageDtoBuilder;
import com.nayax.borsch.validation.config.AssortmentValidationConfig;
import com.nayax.borsch.validation.config.PageIdValidationConfig;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.config.ConfigRepo;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssortmentService {

    @Autowired
    private RepositoryAssortmentImpl assortmentRepository;

    @Autowired
    private RepositoryShawarmaTypeImpl shawarmaType;

    public ResponseDto<PageDto<RespAssortmentDto>> getAllAssortment(int page, int pageSize) {

        List<ErrorDto> errorsPage = PageIdValidationConfig.getValidatorPageId().validate(page, ValidationAction.PAGING);
        errorsPage.addAll(PageIdValidationConfig.getValidatorPageId().validate(pageSize, ValidationAction.PAGING));
        if (errorsPage.size() > 0) {
            return new ResponseDto<>(errorsPage);
        }
        List<AssortmentRespEntity> assortmentEntity = assortmentRepository.findAll();

        int totalPages = PageDtoBuilder.getTotalPages(pageSize, assortmentEntity.size());

        if (totalPages < page) {
            errorsPage.add(new ErrorDto("Incorrect number page", "page"));
            return new ResponseDto<>(errorsPage);
        }


        List<RespAssortmentDto> dto = assortmentEntity.stream()
                .map(Mappers.getMapper(AssortmentMapper.class)::assortmentEntityToDto)
                .collect(Collectors.toList());

        PageDto<RespAssortmentDto> responsePage = new PageDtoBuilder<RespAssortmentDto>()
                .page(dto)
                .elementsPerPage(pageSize)
                .currentPageNum(page)
                .build();

        return new ResponseDto<>(responsePage);
    }

    public ResponseDto<RespAssortmentDto> updateAssortment(ReqAssortmentUpDto dto) {
        List<ErrorDto> errors = AssortmentValidationConfig.getValidator().validate(dto.getDish(), ValidationAction.ASSORTMENT_UPDATE);
        errors.addAll(ConfigRepo.getRepositoryValidator().validate(dto.getDish(), ValidationAction.DISH_DELETE)); /// check by id

        for (Long l : dto.getRemarks()) {
            errors.addAll(ConfigRepo.getRepositoryValidator().validate(l, ValidationAction.REMARK_DEL));/// check by id
        }
        for (Long l : dto.getAdditions()) {
            errors.addAll(ConfigRepo.getRepositoryValidator().validate(l, ValidationAction.ADDITIONS_DEL));/// check by id
            return new ResponseDto<>(errors);
        }
        assortmentRepository.update(Mappers.getMapper(AssortmentMapper.class).toAssortmentUpdateEntity(dto));
        AssortmentRespEntity respEntity = new AssortmentRespEntity();
        respEntity.setDish(shawarmaType.findById(dto.getDish()).get());
        Set<Long> ids = new HashSet<>();
        ids.add(respEntity.getDish().getId());
        Map<ShawarmaItemEntity, List<GeneralPriceItemEntity>> rem = assortmentRepository.findAllRemarks(ids);
        ShawarmaItemEntity shawarmaItemEntity = new ShawarmaItemEntity();
        shawarmaItemEntity.setId(dto.getDish());
        respEntity.setRemarks(rem.get(shawarmaItemEntity));
        Map<ShawarmaItemEntity, List<GeneralPriceItemEntity>> add = shawarmaType.getAdditionsByShawarwa(ids);
        respEntity.setAdditions(add.get(shawarmaItemEntity));
        respEntity.setHalfAble(respEntity.getDish().isHalfAble());
        RespAssortmentDto result = Mappers.getMapper(AssortmentMapper.class).assortmentEntityToDto(respEntity);
        return new ResponseDto<>(result);
    }
}
