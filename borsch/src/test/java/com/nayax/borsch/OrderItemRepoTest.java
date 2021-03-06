package com.nayax.borsch;

import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.model.entity.order.OrderEntity;
import com.nayax.borsch.repository.impl.OrderItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrderItemRepoTest {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    public void testAdd() {
        OrderEntity testIn = new OrderEntity();
        testIn.setUserId(7L);
        testIn.setOrderSummaryId(3L);
        testIn.setQuantity(2);
        testIn.setCut(true);
        testIn.setCreationTime(LocalDateTime.of(2020, 10, 10, 10, 10));
        ShawarmaItemEntity dish = new ShawarmaItemEntity();
        dish.setId(11L);
        List<GeneralPriceItemEntity> additions = new ArrayList<>();
        for (int i = 0; i < 4; i += 2) {
            GeneralPriceItemEntity addition = new GeneralPriceItemEntity();
            addition.setId(i + 1L);
            additions.add(addition);
        }
        GeneralPriceItemEntity drink = new GeneralPriceItemEntity();
        drink.setId(2L);
        GeneralPriceItemEntity remark = new GeneralPriceItemEntity();
        remark.setId(3L);
        testIn.setDish(dish);
        testIn.setAdditions(additions);
        testIn.setDrink(drink);
        testIn.setRemark(remark);

        OrderEntity response = orderItemRepository.add(testIn);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isCut());
        Assertions.assertEquals(testIn.getCreationTime(), response.getCreationTime());

    }
}
