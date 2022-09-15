package org.example.entities;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.entities.enums.OrderStatus;

@Data
@Accessors(chain = true)
public class Order {
    private long id;
    private long petId;
    private int quantity;
    private String shipDate;
    private String status;
    private boolean complete;
}
