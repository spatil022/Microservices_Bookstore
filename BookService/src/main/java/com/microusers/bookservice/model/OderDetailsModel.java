package com.microusers.bookservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OderDetailsModel implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2",strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    public UUID id;

    public Integer orderId;
    public Double totalPrice;

    public LocalDate orderPlacedDate;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cartId")
    public CartDetails cart;

    @JsonIgnore
    @Type(type="uuid-char")
    public UUID userId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer")
    public CustomerDetails customer;

    @JsonIgnore
    @OneToMany(mappedBy = "orderDetails")
    List<BookCartDetails> bookCartDetails;

    public OderDetailsModel(Integer orderId, Double totalPrice, LocalDate orderPlacedDate, CartDetails cart, UUID userId, CustomerDetails customer, List<BookCartDetails> bookCartDetails) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.orderPlacedDate=LocalDate.now();
        this.cart = cart;
        this.userId=userId;
        this.customer = customer;
        this.bookCartDetails = bookCartDetails;
    }
}
