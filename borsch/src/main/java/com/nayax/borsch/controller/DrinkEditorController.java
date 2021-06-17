package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimpleItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespPriceItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.dto.order.response.RespPaymentInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/drink")
public class DrinkEditorController {

    private RespSimplePriceItemDto getRespSimplePriceItemDto() {
        RespSimplePriceItemDto dto = new RespSimplePriceItemDto();
        dto.setId(18L);
        dto.setPrice(new BigDecimal("101"));
        dto.setName("Coca Cola");
        return dto;
    }

    private RespSimplePriceItemDto getRespSimplePriceItemDto2() {
        RespSimplePriceItemDto dto = new RespSimplePriceItemDto();
        dto.setId(2L);
        dto.setPrice(new BigDecimal("95"));
        dto.setName("Fanta");
        return dto;
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<RespSimplePriceItemDto>>> getDrink(@RequestParam int page, @RequestParam int pageSize) {
        RespSimplePriceItemDto mock = getRespSimplePriceItemDto();
        RespSimplePriceItemDto mock2 = getRespSimplePriceItemDto2();
        List<RespSimplePriceItemDto> listMock = List.of(mock, mock2, mock, mock2, mock);
        ResponseDto<List<RespSimplePriceItemDto>> response = new ResponseDto<>(listMock);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> editDrink(@RequestBody ReqSimplePriceItemUpDto dto) {
        RespSimplePriceItemDto mock2 = getRespSimplePriceItemDto2();
        return ResponseEntity.ok(new ResponseDto<>(mock2));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> addDrink(@RequestBody ReqSimplePriceItemAddDto dto) {
        RespSimplePriceItemDto mock = getRespSimplePriceItemDto();
        return ResponseEntity.ok(new ResponseDto<>(mock));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto<Boolean>> deleteDrink(@RequestParam Long id) {
        return ResponseEntity.ok(new ResponseDto<>(true));
    }
}
