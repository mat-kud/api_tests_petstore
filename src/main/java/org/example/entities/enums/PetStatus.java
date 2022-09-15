package org.example.entities.enums;

public enum PetStatus {
    AVAILABLE, PENDING, SOLD;

    public String nameLowerCase(){
        return name().toLowerCase();
    }
}
