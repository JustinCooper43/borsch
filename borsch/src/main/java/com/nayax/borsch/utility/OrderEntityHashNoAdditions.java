package com.nayax.borsch.utility;

import com.nayax.borsch.model.entity.order.OrderEntity;

public class OrderEntityHashNoAdditions extends OrderEntity {
    @Override
    public boolean equals(Object o) {
        if (o instanceof OrderEntityHashNoAdditions) {
            OrderEntityHashNoAdditions temp = (OrderEntityHashNoAdditions) o;
            return temp.getDish().equals(this.getDish()) && temp.getDrink().equals(this.getDrink()) && temp.getRemark().equals(this.getRemark())
                    && temp.getOrderId().equals(this.getOrderId()) && temp.getQuantity().equals(this.getQuantity())
                    && temp.isCut() == this.isCut();
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return (int) (this.getDish().getId() + this.getDrink().getId() + this.getRemark().getId() + this.getOrderId() + this.getQuantity()) * 31;
    }
}
