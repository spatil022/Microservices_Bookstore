package com.microusers.bookservice.controller;

import com.microusers.bookservice.dto.CustomerDetailsDto;
import com.microusers.bookservice.dto.ResponseDto;
import com.microusers.bookservice.model.CustomerDetails;
import com.microusers.bookservice.services.ICustomerService;
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
@RequestMapping("/customer")
@ComponentScan
@EnableAutoConfiguration
public class CustomerController {

    @Autowired
    ICustomerService customerService;

    @PostMapping("/addDetail_customer")
    public ResponseEntity<ResponseDto> addCustomerDetails(@RequestBody @Valid CustomerDetailsDto customerDetailsDto,
                                                          @RequestHeader(value = "token",required = false)String token,
                                                          BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<ResponseDto>(new ResponseDto(bindingResult.getAllErrors().get(0).
                    getDefaultMessage(),"100",null),
                    HttpStatus.BAD_REQUEST);
        }

        CustomerDetails customerDetails =customerService.addCustomerDetails(customerDetailsDto,token);

        return new ResponseEntity (new ResponseDto("CUSTOMER DETAILS ADDED SUCCESFULLY : ",
                "200",customerDetails),
                HttpStatus.CREATED);

    }


    @GetMapping("/getall_details")
    public ResponseEntity<List<CustomerDetails>> getAllCustomerDetails(@RequestHeader(value = "token", required = false) String Token){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustumerDetails(Token));

    }


}

