package com.nayax.borsch.controller;


import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.service.impl.ShavarmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishEditorController {

    @Autowired
    ShavarmaService shavarmaService;

    @PostMapping
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> addDish(@RequestBody ReqSimplePriceItemAddDto dto) {
        return ResponseEntity.ok(shavarmaService.addDish(dto));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<PageDto<RespSimplePriceItemDto>>> getDish(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return ResponseEntity.ok(shavarmaService.getDishByPage(page, pageSize));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> updateDish(@PathVariable(value = "id") Long id, @RequestParam ReqSimplePriceItemUpDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(shavarmaService.updateDish(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> deleteDish(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(shavarmaService.deleteDish(id));
    }
}
