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
import com.nayax.borsch.repository.impl.RepositoryOrderSummaryImpl;
import com.nayax.borsch.repository.impl.RepositoryShawarmaTypeImpl;
import com.nayax.borsch.utility.PageDtoBuilder;
import com.nayax.borsch.validation.config.AssortmentValidationConfig;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.testvalid.config.ConfigRepo;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AssortmentService {

    @Autowired
    private RepositoryAssortmentImpl assortmentRepository;

    @Autowired
    RepositoryShawarmaTypeImpl shawarmaType;

    public ResponseDto<PageDto<RespAssortmentDto>> getAllAssortment(int page,int pageSize){

        List<AssortmentRespEntity> assortmentEntity = assortmentRepository.findAll();

        int totalElements = assortmentEntity.size();
        int pageFrom = (page - 1) * pageSize;
        int pageTo = Math.min(pageFrom + pageSize, totalElements);
        int totalPages = totalElements % pageSize == 0 ?
                totalElements / pageSize :
                totalElements / pageSize + 1;
        assortmentEntity = assortmentEntity.subList(pageFrom, pageTo);


        List<RespAssortmentDto> dto = assortmentEntity.stream()
                .map(Mappers.getMapper(AssortmentMapper.class)::assortmentEntityToDto)
                .collect(Collectors.toList());

        PageDto<RespAssortmentDto> responsePage = new PageDtoBuilder<RespAssortmentDto>()
                .page(dto)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .elementsPerPage(pageSize)
                .currentPageNum(page)
                .build();

        return new ResponseDto<>(responsePage);
    }


    public ResponseDto<RespAssortmentDto> updateAssortment(ReqAssortmentUpDto dto){
        List<ErrorDto> errors = AssortmentValidationConfig.getValidator().validate(dto.getDish(), ValidationAction.ASSORTMEN_UPDATE);
        for (Long l: dto.getRemarks()) {
            errors.addAll(ConfigRepo.getValidatorRemark().validate(l,ValidationAction.REMARK_UPDATE));
        }
        for (Long l : dto.getAdditions()){
            errors.addAll(ConfigRepo.getValidatorRemark().validate(l,ValidationAction.ADDITIONS_UPDATE));
        }
        assortmentRepository.update(Mappers.getMapper(AssortmentMapper.class).toAssortmentUpdateEntity(dto));
        AssortmentRespEntity respEntity = new AssortmentRespEntity();
        respEntity.setDish(shawarmaType.findById(dto.getDish()).get());
        Set<Long> ids = new HashSet<>();
        ids.add(respEntity.getDish().getId());
        Map<ShawarmaItemEntity,List<GeneralPriceItemEntity>> rem = assortmentRepository.findAllRemarks(ids);
        ShawarmaItemEntity shawarmaItemEntity = new ShawarmaItemEntity();
        shawarmaItemEntity.setId(dto.getDish());
        respEntity.setRemarks(rem.get(shawarmaItemEntity));
        Map<ShawarmaItemEntity,List<GeneralPriceItemEntity>> add = shawarmaType.getAdditionsByShawarwa(ids);
        respEntity.setAdditions(add.get(shawarmaItemEntity));
        respEntity.setHalfAble(respEntity.getDish().isHalfAble());
        RespAssortmentDto result = Mappers.getMapper(AssortmentMapper.class).assortmentEntityToDto(respEntity);
        return new ResponseDto<>(result);
    }
}
