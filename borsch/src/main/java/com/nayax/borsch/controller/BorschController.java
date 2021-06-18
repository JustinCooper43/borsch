package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.repository.impl.RepositoryShawarmaTypeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vlad")
public class BorschController {
    @GetMapping("/hello")
    public ResponseEntity<?> greetings() {
        return ResponseEntity.ok().body(new RespUserDto());
    }

    @Autowired
    private RepositoryShawarmaTypeImpl shawarmaType;

    @GetMapping("/user")
    public ResponseEntity<ResponseDto<ShawarmaItemEntity>> getUserExample() {
        RespUserDto respUserDto = new RespUserDto();
        respUserDto.setId(1L);
        respUserDto.seteMail("efil@gmail.com");
        respUserDto.setLastName("John Dou");
        Optional<ShawarmaItemEntity> entity = shawarmaType.findById(103L);
        System.out.println(entity.get());

        List<ShawarmaItemEntity> list = shawarmaType.findAll();
        System.out.println("List: ");
        list.forEach(System.out::println);

        ShawarmaItemEntity shawarmaItemEntity = new ShawarmaItemEntity();
        shawarmaItemEntity.setId(22L);
//        shawarmaItemEntity.setHalfAble(true);
        shawarmaItemEntity.setName("Updated");
        shawarmaItemEntity.setPrice(new BigDecimal("0"));
        ShawarmaItemEntity updated = shawarmaType.update(shawarmaItemEntity);
        return ResponseEntity.ok().body(new ResponseDto<>(entity.get()));
    }
}
