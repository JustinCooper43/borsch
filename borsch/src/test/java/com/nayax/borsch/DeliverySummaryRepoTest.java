package com.nayax.borsch;

import com.nayax.borsch.model.entity.order.OrderEntity;
import com.nayax.borsch.model.entity.order.OrderSumTimerEntity;
import com.nayax.borsch.repository.impl.DeliverySummaryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeliverySummaryRepoTest {
    @Autowired
    DeliverySummaryRepository deliverySummaryRepository;

    @Test
    public void dummySummaryTest() {
        List<OrderEntity> response = deliverySummaryRepository.getByOrderSummaryId(3L);
        Assertions.assertNotEquals(0, response.size());
    }

    @Test
    public void dummyTimerTest() {
        OrderSumTimerEntity response = deliverySummaryRepository.getTimerBeforeDate(LocalDateTime.now());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getCashier());
    }
}
