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
@RequestMapping("/drink")
public class DrinkController {

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

    //TODO revise mapping
    @GetMapping("/get")
    public ResponseEntity<ResponseDto<PageDto<RespSimplePriceItemDto>>> getDrink(@RequestParam Integer page, @RequestParam Integer pageSize) {
        RespSimplePriceItemDto mock = getRespSimplePriceItemDto();
        RespSimplePriceItemDto mock2 = getRespSimplePriceItemDto2();
        List<RespSimplePriceItemDto> listMock = List.of(mock, mock2, mock, mock2, mock);
        PageDto<RespSimplePriceItemDto> pageDto = new PageDto<>(listMock);
        pageDto.setTotalElements(10 * pageSize);
        pageDto.setTotalPages(10);
        pageDto.setElementsPerPage(pageSize);
        pageDto.setCurrentPageNumber(page);
        ResponseDto<PageDto<RespSimplePriceItemDto>> response = new ResponseDto<>(pageDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/edit")
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> editDrink(@RequestBody ReqSimplePriceItemUpDto dto) {
        RespSimplePriceItemDto mock2 = getRespSimplePriceItemDto2();
        return ResponseEntity.ok(new ResponseDto<>(mock2));
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> addDrink(@RequestBody ReqSimplePriceItemAddDto dto) {
        RespSimplePriceItemDto mock = getRespSimplePriceItemDto();
        return ResponseEntity.ok(new ResponseDto<>(mock));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto<Boolean>> deleteDrink(@RequestParam Long id) {
        return ResponseEntity.ok(new ResponseDto<>(true));
    }
}
