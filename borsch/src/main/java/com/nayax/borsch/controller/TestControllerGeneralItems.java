package com.nayax.borsch.controller;


import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemAddDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.repository.impl.AdditionsRepository;
import com.nayax.borsch.repository.impl.TablesType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping
public class TestControllerGeneralItems {

    @Autowired
    AdditionsRepository additionsRepository;

    @PostMapping
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> addAddition(@RequestBody ReqSimplePriceItemAddDto dto) {

        GeneralPriceItemEntity entity = new GeneralPriceItemEntity();

        entity.setPrice(new BigDecimal("1223"));
        entity.setName("Pizza");
        additionsRepository.add(entity, TablesType.ADDIT);

        RespSimplePriceItemDto mock = getRespSimplePriceItemDto();
        return ResponseEntity.ok(new ResponseDto<>(mock));

    }

    private RespSimplePriceItemDto getRespSimplePriceItemDto() {
        RespSimplePriceItemDto dto = new RespSimplePriceItemDto();
        dto.setId(18L);
        dto.setPrice(new BigDecimal("101"));
        dto.setName("Картошка фри");
        return dto;
    }
}
