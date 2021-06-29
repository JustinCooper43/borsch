package com.nayax.borsch.service.impl;

import com.nayax.borsch.mapper.AssortmentMapper;
import com.nayax.borsch.mapper.SimpleItemsMapper;
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

    @Autowired
    RepositoryOrderSummaryImpl repositoryOrderSummary;

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


    public ResponseDto<RespAssortmentDto> updateAssortment(Long id, ReqAssortmentUpDto reqAssortmentUpDto){
        assortmentRepository.update(id,Mappers.getMapper(AssortmentMapper.class).toAssortmentUpdateEntity(reqAssortmentUpDto));
        AssortmentRespEntity respEntity = new AssortmentRespEntity();
        respEntity.setDish(shawarmaType.findById(id).get());
        Set<Long> ids = new HashSet();
        ids.add(id);
        Map<ShawarmaItemEntity,List<GeneralPriceItemEntity>> rem = assortmentRepository.findAllRemarks(ids);
        ShawarmaItemEntity shawarmaItemEntity = new ShawarmaItemEntity();
        shawarmaItemEntity.setId(id);
        respEntity.setRemarks(rem.get(shawarmaItemEntity));
        Map<ShawarmaItemEntity,List<GeneralPriceItemEntity>> add = shawarmaType.getAdditionsByShawarwa(ids);
        respEntity.setAdditions(add.get(shawarmaItemEntity));
        respEntity.setHalfAble(respEntity.getDish().isHalfAble());
        RespAssortmentDto dto = Mappers.getMapper(AssortmentMapper.class).assortmentEntityToDto(respEntity);
        return new ResponseDto<>(dto);
    }
}