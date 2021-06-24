package com.nayax.borsch.utility;

import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.order.OrderEntity;

public class OrderEntityHashNoOrderUserTime extends OrderEntity {
    @Override
    public boolean equals(Object o) {
        if (o instanceof OrderEntityHashNoOrderUserTime) {
            OrderEntityHashNoOrderUserTime temp = (OrderEntityHashNoOrderUserTime) o;
            int matchedItems = 0;
            for (GeneralPriceItemEntity e : temp.getAdditions()) {
                if (this.getAdditions().stream().anyMatch(i -> i.equals(e))) {
                    matchedItems++;
                }
            }
            return this.getAdditions().size() == matchedItems && temp.getDish().equals(this.getDish())
                    && temp.getDrink().equals(this.getDrink()) && temp.getRemark().equals(this.getRemark())
                    && temp.isCut() == this.isCut();
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        int listHash = this.getAdditions().stream()
                .reduce(0, (h, a) -> h + a.hashCode(), Integer::sum);
        return (int) (this.getDish().getId() + this.getDrink().getId() + this.getRemark().getId() + listHash) * 31;
    }
}
