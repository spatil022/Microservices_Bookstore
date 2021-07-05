package com.microusers.bookservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "wishlistitems")
public class WishListItems implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2",strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID wishListItemsId;


    private String addedToWishtDate;

    @ManyToOne()
    @JoinColumn(name = "bookId")
    private BookDetailsModel book;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "wishListId")
    private WishList wishList;

//    public WishListItems(BookDetailsModel book, WishList wishList) {
//        this.book = book;
//        this.wishList = wishList;
//    }

    public WishListItems(UUID book) {
        this.addedToWishtDate=  LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
    }
}
