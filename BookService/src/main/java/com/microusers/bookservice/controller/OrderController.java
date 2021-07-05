package com.microusers.bookservice.controller;

import com.microusers.bookservice.dto.ResponseDto;
import com.microusers.bookservice.model.BookCartDetails;
import com.microusers.bookservice.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/order")
@ComponentScan
@EnableAutoConfiguration
public class OrderController {
    @Autowired
    IOrderService orderService;


    @PostMapping("/addorder/")
    public ResponseEntity<ResponseDto>  addAnOrder(@RequestParam Double totalPrice,
                                                   @RequestHeader(value = "token", required = false) String token){

        String oderDetailsModelMessage=orderService.placeAnOrder(totalPrice,token);
        return new ResponseEntity (new ResponseDto("Order Placed succesfully : ",
                "200",oderDetailsModelMessage),
                HttpStatus.CREATED);

    }

    @GetMapping("/getall_order_details")
    public ResponseEntity<List<BookCartDetails>> fetchAllOrderOfParticularUser(@RequestHeader(value = "token", required = false) String token){

        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders(token));
    }
}