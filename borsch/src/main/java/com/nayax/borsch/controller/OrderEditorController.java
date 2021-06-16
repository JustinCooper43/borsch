package com.nayax.borsch.controller;


import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.response.RespAssortmentItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespPriceItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespPriceListDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@RestController
public class OrderEditorController {

    @GetMapping("/price")
    public ResponseEntity<ResponseDto<RespPriceListDto>> price(){

        RespPriceItemDto respPriceItemDto = new RespPriceItemDto();
        respPriceItemDto.setId(2l);
        respPriceItemDto.setPrice(new BigDecimal("2321.213"));
        respPriceItemDto.setType(0);
        respPriceItemDto.setName("Шаурма с курицей");
        RespPriceListDto respPriceItemDtos = new RespPriceListDto();
        respPriceItemDtos.setList(List.of(respPriceItemDto,respPriceItemDto,respPriceItemDto,respPriceItemDto,respPriceItemDto,respPriceItemDto));
        ResponseDto <RespPriceListDto> responseDto = new ResponseDto<>(respPriceItemDtos);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/dish/dropdown")
    public ResponseEntity<ResponseDto<List<RespAssortmentItemDto>>> dishDropdown(){
        RespAssortmentItemDto respAssortmentItemDto = new RespAssortmentItemDto();
        respAssortmentItemDto.setId(2l);
        respAssortmentItemDto.setName("Шаурма с курицей");
        respAssortmentItemDto.setHalfable(true);
        respAssortmentItemDto.setPrice(new BigDecimal("60.22"));
        List<RespAssortmentItemDto> list = List.of(respAssortmentItemDto,respAssortmentItemDto,respAssortmentItemDto,respAssortmentItemDto,respAssortmentItemDto);
         ResponseDto<List<RespAssortmentItemDto>> responseDto = new ResponseDto<>(list);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/additional/dropdown")
    public ResponseEntity<ResponseDto<List<RespSimplePriceItemDto>>> additionalDropdown(@RequestParam Long dishId){
         RespSimplePriceItemDto priceItemDto = new RespSimplePriceItemDto();
         priceItemDto.setId(2l);
         priceItemDto.setName("Соус острый");
         priceItemDto.setPrice(new BigDecimal("10.221"));
        List<RespSimplePriceItemDto> list = List.of(priceItemDto ,priceItemDto ,priceItemDto ,priceItemDto ,priceItemDto );
        ResponseDto<List<RespSimplePriceItemDto>> responseDto = new ResponseDto<>(list);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/drink/dropdown")
    public ResponseEntity<ResponseDto<List<RespSimplePriceItemDto>>> drinkDropdown(){
         RespSimplePriceItemDto priceItemDto = new RespSimplePriceItemDto();
         priceItemDto.setId(3l);
         priceItemDto.setName("Coca-cola");
         priceItemDto.setPrice(new BigDecimal("20.221"));
        List<RespSimplePriceItemDto> list = List.of(priceItemDto ,priceItemDto ,priceItemDto ,priceItemDto ,priceItemDto );
        ResponseDto<List<RespSimplePriceItemDto>> responseDto = new ResponseDto<>(list);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/remark/dropdown")
    public ResponseEntity<ResponseDto<List<RespSimplePriceItemDto>>> remarkDropdown(){
         RespSimplePriceItemDto priceItemDto = new RespSimplePriceItemDto();
         priceItemDto.setId(12l);
         priceItemDto.setName("Побольше соуса");
         priceItemDto.setPrice(new BigDecimal("1.221"));
        List<RespSimplePriceItemDto> list = List.of(priceItemDto ,priceItemDto ,priceItemDto ,priceItemDto ,priceItemDto );
        ResponseDto<List<RespSimplePriceItemDto>> responseDto = new ResponseDto<>(list);
        return ResponseEntity.ok(responseDto);
    }






}
