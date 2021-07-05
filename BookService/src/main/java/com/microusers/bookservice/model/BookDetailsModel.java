package com.microusers.bookservice.model;

import com.microusers.bookservice.dto.BookDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookDetailsModel {

    @Id
    @GeneratedValue(generator = "uuid2",strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    public UUID bookId;

    public String image;
    public String bookName;
    public String authorName;
    public String description;
    public double bookPrice;
    public Integer quantity;
    public int rating;
    public int publishingYear;
    public boolean isAdded;
    public boolean isAddedToWish;
    public LocalDateTime createdAt = LocalDateTime.now();


 @OneToMany( mappedBy = "bookDetailsModel")
 public List<BookCartDetails> bookCartDetails;



    public BookDetailsModel( String image,String bookName, String authorName, String description, double bookPrice, Integer quantity, int rating,int publishingYear) {
        this.image=image;
        this.bookName = bookName;
        this.authorName = authorName;
        this.description = description;
        this.bookPrice = bookPrice;
        this.quantity = quantity;
        this.rating = rating;
        this.publishingYear = publishingYear;

    }

    public BookDetailsModel(BookDto bookDTO) {
        this.bookName = bookDTO.bookName;
        this.authorName = bookDTO.authorName;
        this.bookPrice = bookDTO.bookPrice;
        this.rating = bookDTO.rating;
        this.quantity = bookDTO.quantity;
        this.description = bookDTO.description;
        this.publishingYear = bookDTO.publishingYear;
        this.image=bookDTO.image;
    }


    public BookDetailsModel(BookDetailsModel bookDetailsModel) {
        this.bookId=bookDetailsModel.getBookId();
        this.bookName=bookDetailsModel.getBookName();
        this.authorName=bookDetailsModel.getAuthorName();
        this.bookPrice=bookDetailsModel.getBookPrice();
        this.rating=bookDetailsModel.getRating();
        this.quantity=bookDetailsModel.getQuantity();
        this.description =bookDetailsModel.getDescription();
        this.publishingYear=bookDetailsModel.getPublishingYear();
        this.createdAt=bookDetailsModel.getCreatedAt();
        this.image=bookDetailsModel.getImage();
        this.isAdded=bookDetailsModel.isAdded();
        this.isAddedToWish=bookDetailsModel.isAddedToWish();
        this.bookCartDetails=bookDetailsModel.getBookCartDetails();
    }


    public BookDetailsModel(UUID bookId) {
        this.bookId=bookId;
    }
}