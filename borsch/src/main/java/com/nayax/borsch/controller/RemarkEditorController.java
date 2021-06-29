package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimpleItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimpleItemUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import com.nayax.borsch.repository.impl.TablesType;
import com.nayax.borsch.service.impl.DrinkAdditionService;
import com.nayax.borsch.service.impl.RemarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/remark")
public class RemarkEditorController {

    @Autowired
    RemarksService remarksService;

    @GetMapping
    public ResponseEntity<ResponseDto<PageDto<RespSimpleItemDto>>> getRemark(@RequestParam int page, @RequestParam int pageSize) {
        return ResponseEntity.ok(remarksService.getRemarkItemPage(page, pageSize, TablesType.REMARK));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<RespSimpleItemDto>> editRemark(@PathVariable(value = "id") Long id, @RequestBody ReqSimpleItemUpDto dto) {
        dto.setId(id);
        return ResponseEntity.ok(remarksService.editRemarkItem(dto, TablesType.REMARK));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<RespSimpleItemDto>> addRemark(@RequestBody ReqSimpleItemAddDto dto) {
        return ResponseEntity.ok(remarksService.addRemarkItem(dto, TablesType.REMARK));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<RespSimpleItemDto>> deleteRemark(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(remarksService.delRemarkItemById(id, TablesType.REMARK));
    }
}
