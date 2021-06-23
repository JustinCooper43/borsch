package com.nayax.borsch;

import com.nayax.borsch.model.entity.order.OrderEntity;
import com.nayax.borsch.repository.impl.DeliverySummaryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeliverySummaryRepoTest {
    @Autowired
    DeliverySummaryRepository deliverySummaryRepository;

    @Test
    public void dummyTest() {
        List<OrderEntity> response = deliverySummaryRepository.getByOrderSummaryId(3L);
        Assertions.assertNotEquals(0, response.size());
    }
}
