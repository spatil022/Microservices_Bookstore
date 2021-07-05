package com.microusers.bookservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CouponDto {

    public String couponsType;
    public Double discountPrice;
    public String description;
    public String expireCouponDate;
    public Double minimumPrice;

    public CouponDto(String couponsType, Double discountPrice, String description, String expireCouponDate, Double minimumPrice) {
        this.couponsType = couponsType;
        this.discountPrice = discountPrice;
        this.description = description;
        this.expireCouponDate = expireCouponDate;
        this.minimumPrice = minimumPrice;
    }
}
