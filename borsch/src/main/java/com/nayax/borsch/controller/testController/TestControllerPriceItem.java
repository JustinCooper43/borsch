package com.nayax.borsch.controller.testController;


import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.response.RespPriceItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.repository.impl.PriceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test_price")
public class TestControllerPriceItem {

    @Autowired
    PriceItemRepository priceItemRepository;

    @GetMapping("/get")
    public ResponseEntity<ResponseDto<List<RespPriceItemDto>>> getAllPrice(){
        List<RespPriceItemDto> listRespDto = new ArrayList<>();
        listRespDto.add(generatePriceDto());
        listRespDto.add(generatePriceDto());
        listRespDto.add(generatePriceDto());
        ResponseDto<List<RespPriceItemDto>> result = new ResponseDto<>(listRespDto);

        priceItemRepository.getAll();

        return ResponseEntity.ok(result);
    }

    private RespPriceItemDto generatePriceDto(){
        RespPriceItemDto dto = new RespPriceItemDto();
        dto.setId(1L);
        dto.setPrice(new BigDecimal("9090"));
        dto.setName("PriceItemName");
        dto.setType(0);
        return dto;
    }


}
