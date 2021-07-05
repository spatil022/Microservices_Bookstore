package com.microusers.bookservice.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CartDto {

    public UUID bookId;


    public Integer quantity;
    public Double totalPrice;

    public CartDto() {
    }

    public CartDto(UUID bookId, Integer quantity,Double totalPrice) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
