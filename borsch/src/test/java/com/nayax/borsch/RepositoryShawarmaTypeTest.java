package com.nayax.borsch;

import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.repository.impl.RepositoryShawarmaTypeImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class RepositoryShawarmaTypeTest {
    private static RepositoryShawarmaTypeImpl repositoryShawarmaType;

    @BeforeAll
    public static void init(){
        repositoryShawarmaType = new RepositoryShawarmaTypeImpl();
    }


    @Test
    public void testAddShawarmaType(){
        ShawarmaItemEntity add = new ShawarmaItemEntity();
        add.setName("TestedAdd2");
        add.setPrice(new BigDecimal("1"));
        add.setHalfAble(true);

        ShawarmaItemEntity added = repositoryShawarmaType.add(add);
        System.out.println(added);
//        Assertions.assertEquals(27,added.getId());
    }
}
