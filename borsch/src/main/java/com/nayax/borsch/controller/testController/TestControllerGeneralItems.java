package com.nayax.borsch.controller.testController;


import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespAssortmentDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.repository.impl.AdditionsRepository;
import com.nayax.borsch.repository.impl.TablesType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestControllerGeneralItems {

    @Autowired
    AdditionsRepository additionsRepository;

    @PostMapping
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> addAddition(@RequestBody ReqSimplePriceItemAddDto dto) {

        GeneralPriceItemEntity entity = new GeneralPriceItemEntity();

        entity.setPrice(new BigDecimal("1223"));
        entity.setName("Pizza");
        additionsRepository.add(entity, TablesType.ADDITION);

        RespSimplePriceItemDto mock = getRespSimplePriceItemDto();
        return ResponseEntity.ok(new ResponseDto<>(mock));

    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> getByIdAddition(@PathVariable(value = "id") Long id) {
        GeneralPriceItemEntity entity = new GeneralPriceItemEntity();
        entity.setPrice(new BigDecimal("99"));
        entity.setName("Cake");
        additionsRepository.findById(id, TablesType.ADDITION);

        RespSimplePriceItemDto mock = getRespSimplePriceItemDto();
        return ResponseEntity.ok(new ResponseDto<>(mock));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<RespSimplePriceItemDto>>> getAllAddition() {
        additionsRepository.findAll(TablesType.ADDITION);

        RespSimplePriceItemDto mock1 = getRespSimplePriceItemDto();
        RespSimplePriceItemDto mock2 = getRespSimplePriceItemDto();
        RespSimplePriceItemDto mock3 = getRespSimplePriceItemDto();
        List<RespSimplePriceItemDto> listResult = List.of(mock1, mock2, mock3);
        return ResponseEntity.ok(new ResponseDto<>(listResult));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> deleted(@PathVariable(value = "id") Long id) {
        additionsRepository.delete(id, TablesType.ADDITION);
        RespSimplePriceItemDto mock = getRespSimplePriceItemDto();
        return ResponseEntity.ok(new ResponseDto<>(mock));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> editAddition(@PathVariable(value = "id") Long id, @RequestBody ReqSimplePriceItemUpDto dto) {
        GeneralPriceItemEntity entity = new GeneralPriceItemEntity();
        entity.setPrice(new BigDecimal("222"));
        entity.setName("TestTest");
        entity.setId(id);

        additionsRepository.update(entity, TablesType.ADDITION);
        dto.setId(id);
        RespSimplePriceItemDto mock = getRespSimplePriceItemDto();
        return ResponseEntity.ok(new ResponseDto<>(mock));
    }

    @GetMapping("/page")
    public ResponseEntity<ResponseDto<PageDto<RespSimplePriceItemDto>>> getAllPage(@RequestParam int page, @RequestParam int pageSize) {
        RespSimplePriceItemDto priceItemDto = new RespSimplePriceItemDto();
        priceItemDto.setPrice(new BigDecimal("937"));
        priceItemDto.setName("TestPageGen");

        additionsRepository.findAllPage(page, pageSize, TablesType.ADDITION);

        PageDto<RespSimplePriceItemDto> pageDto = PageDto.getPagedList(page, pageSize,
                List.of(priceItemDto, priceItemDto, priceItemDto, priceItemDto, priceItemDto, priceItemDto, priceItemDto, priceItemDto));
        ResponseDto<PageDto<RespSimplePriceItemDto>> responseDto = new ResponseDto<>(pageDto);
        return ResponseEntity.ok(responseDto);
    }

    private RespSimplePriceItemDto getRespSimplePriceItemDto() {
        RespSimplePriceItemDto dto = new RespSimplePriceItemDto();
        dto.setId(18L);
        dto.setPrice(new BigDecimal("101"));
        dto.setName("???????????????? ??????");
        return dto;
    }


}
