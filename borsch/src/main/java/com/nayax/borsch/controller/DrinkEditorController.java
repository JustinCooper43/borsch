package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.repository.impl.TablesType;
import com.nayax.borsch.service.impl.DrinkAdditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/drink")
public class DrinkEditorController {

    @Autowired
    DrinkAdditionService drinkAdditionService;

    @GetMapping
    public ResponseEntity<ResponseDto<PageDto<RespSimplePriceItemDto>>> getDrink(@RequestParam int page, @RequestParam int pageSize) {
        return ResponseEntity.ok(drinkAdditionService.getGeneralItemPage(page, pageSize, TablesType.EXTRAITEM));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> editDrink(@PathVariable(value = "id") Long id, @RequestBody ReqSimplePriceItemUpDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(drinkAdditionService.editGeneralItem(dto, TablesType.EXTRAITEM));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> addDrink(@RequestBody ReqSimplePriceItemAddDto dto) {
        return ResponseEntity.ok(drinkAdditionService.addGeneralItem(dto, TablesType.EXTRAITEM));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> deleteDrink(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(drinkAdditionService.delGeneralItemById(id, TablesType.EXTRAITEM));
    }
}
