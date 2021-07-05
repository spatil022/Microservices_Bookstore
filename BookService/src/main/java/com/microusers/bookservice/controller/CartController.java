package com.microusers.bookservice.controller;

import com.microusers.bookservice.dto.CartDto;
import com.microusers.bookservice.dto.ResponseDto;
import com.microusers.bookservice.dto.UpdateCartDetailDto;
import com.microusers.bookservice.model.BookCartSummary;
import com.microusers.bookservice.model.CartDetails;
import com.microusers.bookservice.services.ICartService;
import com.microusers.bookservice.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/cart")
@ComponentScan
@EnableAutoConfiguration
public class CartController {

    @Autowired
    ICartService cartService;


    Token token = new Token();

    @PostMapping("/addtocart")
    public ResponseEntity<ResponseDto> addBookToCart(@RequestBody CartDto cartDto,
                                                     @RequestHeader(value = "token", required = false) String token) {
        String message = cartService.addToCart(cartDto, token);
        return new ResponseEntity (new ResponseDto(message,
                "200",null),
                HttpStatus.CREATED);
    }

    @GetMapping("/allbooksincart")
    public ResponseEntity<List<BookCartSummary>> getAllBooksInCart(@RequestHeader(value = "token", required = false) String Token) {

        return ResponseEntity.status(HttpStatus.OK).body(cartService.showAllBooksInCart(Token));

    }

    @DeleteMapping("/deletecart/{id}")
    public ResponseEntity<ResponseDto> deleteBook(@PathVariable UUID id,
                                                  @RequestHeader(value = "token", required = false) String Token) {
        String message = cartService.deleteCartItem(id, Token);
        ResponseDto responseDto = new ResponseDto(message);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @PutMapping("/updatecartofbook")
    public ResponseEntity updateBookQuantity(@Valid @RequestBody UpdateCartDetailDto cartDto,
                                             @RequestHeader(value = "token") String token) {
        String message = cartService.updateQuantityAndPrice(cartDto, token);
        ResponseDto responseDto = new ResponseDto(message);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @GetMapping("/getcartset")
    public CartDetails setCart(@RequestHeader(value = "token") String token){
        CartDetails cartDetails = cartService.setCart(token);
        return cartDetails;
    }


}
