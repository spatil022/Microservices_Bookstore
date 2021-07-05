package com.microusers.bookservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microusers.bookservice.dto.CartDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Getter
@Setter
@Entity
@AllArgsConstructor
public class BookCartDetails {

    @Id
    @GeneratedValue(generator = "uuid2",strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    public UUID BookCartDetailsId;

    public Integer quantity;
    public Double totalPrice;
    public boolean orderStatus;
    public String addedToCartDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "bookModelId")
    public BookDetailsModel bookDetailsModel;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cartDetailsId")
    public CartDetails cartDetails;

    @ManyToOne
    @JoinColumn(name = "orderDetailsId")
    public OderDetailsModel orderDetails;


    public BookCartDetails(CartDto cartDto){
        this.quantity=cartDto.quantity;
        this.orderStatus= false;
        this.totalPrice=cartDto.totalPrice;
        this.addedToCartDate= LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
    }

    public BookCartDetails() {

    }

    public BookCartDetails(BookCartDetails bookCartDetails) {
        this.BookCartDetailsId=bookCartDetails.getBookCartDetailsId();
        this.quantity=bookCartDetails.getQuantity();
        this.orderStatus=bookCartDetails.orderStatus;
        this.totalPrice=bookCartDetails.getTotalPrice();
        this.bookDetailsModel=bookCartDetails.getBookDetailsModel();
        this.cartDetails=bookCartDetails.getCartDetails();
        this.orderDetails=bookCartDetails.getOrderDetails();
    }
}


