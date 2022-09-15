package org.example.entities.enums;

public enum OrderStatus {
    PLACED, APPROVED, DELIVERED;

    public String nameLowerCase(){
        return name().toLowerCase();
    }
}
