package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimpleItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimpleItemUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/remark")
public class RemarkController {

    private RespSimpleItemDto getRespSimpleItemDto() {
        RespSimpleItemDto dto = new RespSimpleItemDto();
        dto.setId(99L);
        dto.setName("more sauce");
        return dto;
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<RespSimpleItemDto>>> getRemark(@RequestParam int page, @RequestParam int pageSize) {
        RespSimpleItemDto mockDto = getRespSimpleItemDto();
        List<RespSimpleItemDto> listMock = List.of(mockDto, mockDto, mockDto, mockDto, mockDto, mockDto, mockDto, mockDto, mockDto, mockDto);
        return ResponseEntity.ok(new ResponseDto<>(listMock));
    }

    @PutMapping
    public ResponseEntity<ResponseDto<RespSimpleItemDto>> editRemark(@RequestBody ReqSimpleItemUpDto dto) {
        RespSimpleItemDto mockDto = getRespSimpleItemDto();
        return ResponseEntity.ok(new ResponseDto<>(mockDto));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<RespSimpleItemDto>> addRemark(@RequestBody ReqSimpleItemAddDto dto) {
        RespSimpleItemDto mockDto = getRespSimpleItemDto();
        return ResponseEntity.ok(new ResponseDto<>(mockDto));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto<Boolean>> deleteRemark(@RequestParam Long id) {
        return ResponseEntity.ok(new ResponseDto<>(true));
    }
}
