package ru.goltsov.education.model;

import lombok.Data;

@Data
public class OrderEvent {

    private String product;
    private Integer quantity;

}
