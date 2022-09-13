package org.example.entities;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.entities.enums.PetStatus;

@Data
@Accessors(chain = true)
public class Pet {
    private int id;
    private Category category;
    private String name;
    private String[] photoUrls;
    private Tag[] tags;
    private PetStatus status;
}
