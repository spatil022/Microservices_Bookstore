package com.microusers.bookservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microusers.bookservice.dto.CouponDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Coupons implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2",strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    UUID CouponId;

    public String couponsType;
    public Double discountPrice;
    public String description;
    public String expireCouponDate;
    public Double minimumPrice;


    @JsonIgnore
    @OneToMany(mappedBy = "coupons")
    public List<CouponsDetails> couponsDetails;


    public Coupons(String couponsType, Double discountPrice, String description, String expireCouponDate, Double minimumPrice) {
        this.couponsType = couponsType;
        this.discountPrice = discountPrice;
        this.description = description;
        this.expireCouponDate = expireCouponDate;
        this.minimumPrice=minimumPrice;
    }


    public Coupons(CouponDto couponDto) {
        this.couponsType = couponDto.couponsType;
        this.discountPrice = couponDto.discountPrice;
        this.description = couponDto.description;
        this.expireCouponDate = couponDto.expireCouponDate;
        this.minimumPrice = couponDto.minimumPrice;
    }
}
