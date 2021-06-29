package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespAssortmentItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.dto.order.response.RespOrderSumDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;
import com.nayax.borsch.model.entity.assortment.AssortmentRespEntity;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.model.entity.order.OrderSummaryEntity;
import com.nayax.borsch.repository.impl.RepositoryAssortmentImpl;
import com.nayax.borsch.repository.impl.RepositoryOrderSummaryImpl;
import com.nayax.borsch.repository.impl.RepositoryShawarmaTypeImpl;
import com.nayax.borsch.service.impl.OrderSummaryInfoService;
import com.nayax.borsch.service.impl.ShavarmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vlad")
public class BorschController {
    @Autowired
    private RepositoryShawarmaTypeImpl shawarmaType;

    @Autowired
    ShavarmaService shavarmaService;

    @Autowired
    private RepositoryAssortmentImpl assortment;

    @Autowired
    private RepositoryOrderSummaryImpl orderSummary;

    @Autowired
    OrderSummaryInfoService  summaryInfoService;

    @GetMapping("/hello")
    public ResponseEntity<?> greetings() {
       List<AssortmentRespEntity> list = assortment.findAll();
       List<Long> add = List.of(10L,11L,12L);
        List<Long> rem = List.of(4L,1L,5L);
      // AssortmentRespEntity update = assortment.update(11L,add,rem);
        LocalDate localDate = LocalDate.parse("2020-10-10");
//        List<OrderSummaryEntity> orderSum = orderSummary.findAll(date);
        ResponseDto<PageDto<RespOrderSumDto>> dto =  summaryInfoService.getSummaryOrder(localDate,1,1);
        return  ResponseEntity.ok().body(dto.getData().getResponseList());
    }


    @GetMapping("/user")
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> getUserExample() {
//        RespUserDto respUserDto = new RespUserDto();
//        respUserDto.setId(1L);
//        respUserDto.seteMail("efil@gmail.com");
//        respUserDto.setLastName("John Dou");
//        Optional<ShawarmaItemEntity> entity = shawarmaType.findById(15L);
//        System.out.println(entity.get());
//
//        List<ShawarmaItemEntity> list = shawarmaType.findAll();
//        System.out.println("List: ");
//        list.forEach(System.out::println);
//
//        ShawarmaItemEntity shawarmaItemEntity = new ShawarmaItemEntity();
//        shawarmaItemEntity.setId(15L);
//        shawarmaItemEntity.setHalfAble(true);
//        shawarmaItemEntity.setName("армянская22");
//        shawarmaItemEntity.setPrice(new BigDecimal("0"));
//        ShawarmaItemEntity updated = shawarmaType.update(shawarmaItemEntity);
//        System.out.println(updated);


//        System.out.println("Deleted::::::::::::::");
//        Optional<ShawarmaItemEntity> deletedById = shawarmaType.delete(22L);
//        System.out.println(deletedById.get());
//        System.out.println("______________________________________");
//        Optional<ShawarmaItemEntity> deletedByEntity = shawarmaType.delete(shawarmaItemEntity);
//        System.out.println(deletedByEntity.get());


//        System.out.println("add:");
//        ShawarmaItemEntity add = new ShawarmaItemEntity();
//        add.setName("TestedAdd00000000000");
//        add.setPrice(new BigDecimal("1"));
//        add.setHalfAble(true);
//
//        ShawarmaItemEntity added = shawarmaType.add(add);
//        System.out.println(added);

//
//        List<ShawarmaItemEntity> shawarmaItemEntityList = shawarmaType.findAll(1,5);
//        shawarmaItemEntityList.forEach(System.out::println);

        ReqSimplePriceItemUpDto update = new ReqSimplePriceItemUpDto();
        update.setId(10000000L);
        update.setName("qwerty");
        update.setPrice(new BigDecimal("1"));
        ResponseDto<RespSimplePriceItemDto> responseDto = shavarmaService.updateDish(update);

//        RespAssortmentItemDto dto = new RespAssortmentItemDto();
//        dto.setId(entity.get().getId());
//        dto.setName(entity.get().getName());
//        dto.setPrice(entity.get().getPrice());
//        dto.setHalfAble(entity.get().isHalfAble());
        return ResponseEntity.ok().body(responseDto);
    }
}
