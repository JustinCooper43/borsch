package com.nayax.borsch;

import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.repository.impl.RepositoryShawarmaTypeImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryShawarmaTypeTest {
    @Autowired
    private RepositoryShawarmaTypeImpl repositoryShawarmaType;

    @Test
    public void testAddShawarmaType(){
        ShawarmaItemEntity add = new ShawarmaItemEntity();
        add.setName("TestedAdd2");
        add.setPrice(new BigDecimal("1"));
        add.setHalfAble(true);

        ShawarmaItemEntity added = repositoryShawarmaType.add(add);
        System.out.println(added);
        //Assertions.assertEquals(27,added.getId());
    }
}
