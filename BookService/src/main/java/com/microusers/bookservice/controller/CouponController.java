package com.microusers.bookservice.controller;


import com.microusers.bookservice.dto.CouponDto;
import com.microusers.bookservice.dto.ResponseDto;
import com.microusers.bookservice.model.Coupons;
import com.microusers.bookservice.services.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/coupon")
@ComponentScan
@EnableAutoConfiguration
public class CouponController {
    @Autowired
    ICouponService couponService;


    @PostMapping("/addCoupons")
    public ResponseEntity<ResponseDto> addCouponsToDatabase(@RequestBody @Valid CouponDto couponDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<ResponseDto>(new ResponseDto(bindingResult.getAllErrors().get(0).
                    getDefaultMessage(),"100",null),
                    HttpStatus.BAD_REQUEST);
        }
        Coupons coupons = couponService.addCouponsToDatabase(couponDto);
        return new ResponseEntity (new ResponseDto("COUPONS ADDED SUCCESFULLY : ",
                "200",coupons),
                HttpStatus.CREATED);
    }

    @GetMapping("/fetchOrderCoupon")
    public ResponseEntity fetchOrderCoupon(@RequestHeader(value = "token") String token, @RequestParam(name = "totalPrice") Double totalPrice) {
        List<Coupons> orders = couponService.fetchCoupon(token,totalPrice);
        ResponseDto response = new ResponseDto("Coupons Fetched Successfully", orders);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/addCoupon")
    public ResponseEntity addCoupon(@RequestHeader(value = "token") String token,@RequestParam(name = "discountCoupon",defaultValue = "0") String coupon, @RequestParam(name = "totalPrice") Double totalPrice){
        Double coupon1 = couponService.addCoupon(token, coupon, totalPrice);
        ResponseDto response = new ResponseDto("Coupon added successfully",coupon1);
        return new ResponseEntity(response,HttpStatus.OK);
    }
}