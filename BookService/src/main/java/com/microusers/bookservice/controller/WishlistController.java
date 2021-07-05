package com.microusers.bookservice.controller;

import com.microusers.bookservice.dto.ResponseDto;
import com.microusers.bookservice.model.WishList;
import com.microusers.bookservice.model.WishListItems;
import com.microusers.bookservice.services.IWishListService;
import com.sun.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/wishlist")
@ComponentScan
@EnableAutoConfiguration
public class WishlistController {

    @Autowired
    IWishListService wishListService;

    @PostMapping("/wishlist/{bookid}")
    public ResponseEntity<Response> addToWishList(@PathVariable(name = "bookid") UUID bookId, @RequestHeader(value = "token", required = false) String token) {
        String message = wishListService.addToWishList(bookId, token);
        return new ResponseEntity (new ResponseDto(message,
                "200",null),
                HttpStatus.CREATED);
    }


    @GetMapping("/wishlist")
    public  ResponseEntity<List<WishListItems>> fetchWishList(@RequestHeader(value = "token", required = false) String token) {
        return ResponseEntity.status(HttpStatus.OK).body(wishListService.fetchWishList(token));
    }


    @DeleteMapping("/wishlist/{bookid}")
    public ResponseEntity<ResponseDto> deleteBookFromWishList(@PathVariable(name = "bookid") UUID bookId, @RequestHeader(value = "token", required = false) String token) {
        String message = wishListService.deleteBookFromWishList(bookId, token);
        ResponseDto response = new ResponseDto(message);
        return new ResponseEntity(response, HttpStatus.OK);
    }


    @GetMapping("/getwishset")
    public WishList setCart(@RequestHeader(value = "token") String token){
        WishList wishList = wishListService.setWish(token);
        return wishList;
    }

}