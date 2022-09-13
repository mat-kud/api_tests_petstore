package org.example.entities;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.entities.enums.OrderStatus;

@Data
@Accessors(chain = true)
public class Order {
    private int id;
    private int petId;
    private int quantity;
    private String shipDate;
    private OrderStatus status;
    private boolean complete;
}
