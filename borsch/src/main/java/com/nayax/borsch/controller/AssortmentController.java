package com.nayax.borsch.controller;

import com.nayax.borsch.mapper.AssortmentMapper;
import com.nayax.borsch.mapper.AssortmentMapper;
import com.nayax.borsch.mapper.UserMapper;
import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqAssortmentUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespAssortmentDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import com.nayax.borsch.model.entity.assortment.AssortmentRespEntity;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.service.impl.AssortmentService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/assortment")
public class AssortmentController {

    @Autowired
    private AssortmentService assortmentService;

    private List<RespSimpleItemDto> getMockList() {
        RespSimpleItemDto item = new RespSimpleItemDto();
        item.setId(13L);
        item.setName("SimpleItem");
        return List.of(item, item, item, item, item, item, item, item, item, item);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<PageDto<RespAssortmentDto>>> getAssortment(@RequestParam int page, @RequestParam int pageSize) {

        ResponseDto<PageDto<RespAssortmentDto>> list = assortmentService.getAllAssortment(page,pageSize);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<RespAssortmentDto>> updateById(@PathVariable(value = "id") Long id, @RequestBody ReqAssortmentUpDto dto) {
        ResponseDto<RespAssortmentDto> resp = assortmentService.updateAssortment(id,dto);
        return ResponseEntity.ok(resp);
    }

//    @PutMapping
//    public ResponseEntity<ResponseDto<RespAssortmentDto>> addAssortment(@RequestBody ReqAssortmentUpDto dto) {
//        RespAssortmentDto respAssortmentDto = new RespAssortmentDto();
//        respAssortmentDto.setAdditions(getMockList());
//        respAssortmentDto.setRemarks(getMockList());
//        respAssortmentDto.setDish(getMockList().get(0));
//        respAssortmentDto.setHalfAble(true);
        /////
//
//        GeneralPriceItemEntity general = new GeneralPriceItemEntity();
//        general.setId(99L);
//        general.setName("Vlad");
//        general.setPrice(new BigDecimal("1000"));
//        GeneralPriceItemEntity general2 = new GeneralPriceItemEntity();
//        general.setId(992L);
//        general.setName("Vlad2");
//        general.setPrice(new BigDecimal("10002"));
//        AssortmentRespEntity entity = new AssortmentRespEntity();
//        List<GeneralPriceItemEntity> priceItemEntities = new ArrayList<>();
//        priceItemEntities.add(0,general);
//        priceItemEntities.add(1,general2);
//        //////////////
//        entity.setDish(general);
//        entity.setHalfAble(true);
//        entity.setRemarks(priceItemEntities);
//
//        entity.setAdditions(priceItemEntities);
//
//
//
//        RespAssortmentDto testRespDto = Mappers.getMapper(AssortmentMapper.class).assortmentEntityToDto(entity);
        //return ResponseEntity.ok(new ResponseDto<>(respAssortmentDto));
//    }
}
