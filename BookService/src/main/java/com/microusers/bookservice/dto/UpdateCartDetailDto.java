package com.microusers.bookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartDetailDto {

    public UUID bookId;
    public Integer quantity;
    public Double totalPrice;

}
