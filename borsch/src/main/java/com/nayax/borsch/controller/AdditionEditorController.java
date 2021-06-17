package com.nayax.borsch.controller;


import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/addition")
public class AdditionEditorController {


    private RespSimplePriceItemDto getRespSimplePriceItemDto() {
        RespSimplePriceItemDto dto = new RespSimplePriceItemDto();
        dto.setId(18L);
        dto.setPrice(new BigDecimal("101"));
        dto.setName("Картошка фри");
        return dto;
    }

    @GetMapping
    public ResponseEntity<ResponseDto<PageDto<RespSimplePriceItemDto>>> getAddition(@RequestParam int page, @RequestParam int pageSize) {
        RespSimplePriceItemDto mock = getRespSimplePriceItemDto();
        List<RespSimplePriceItemDto> listMock = List.of(mock, mock, mock, mock, mock, mock, mock);
        PageDto<RespSimplePriceItemDto> pageDto = new PageDto<>(listMock);
        pageDto.setTotalElements(10 * pageSize);
        pageDto.setTotalPages(10);
        pageDto.setPageSize(pageSize);
        pageDto.setPage(page);
        ResponseDto<PageDto<RespSimplePriceItemDto>> responseDto = new ResponseDto<>(pageDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> editAddition(@PathVariable(value = "id") Long id, @RequestBody ReqSimplePriceItemUpDto dto) {
        dto.setId(id);
        RespSimplePriceItemDto mock = getRespSimplePriceItemDto();
        return ResponseEntity.ok(new ResponseDto<>(mock));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> addAddition(@RequestBody ReqSimplePriceItemAddDto dto) {
        RespSimplePriceItemDto mock = getRespSimplePriceItemDto();
        return ResponseEntity.ok(new ResponseDto<>(mock));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Boolean>> deleteAddition(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(new ResponseDto<>(true));
    }
}
