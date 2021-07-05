package com.microusers.bookservice.dto;

import javax.validation.constraints.Pattern;

public class CustomerDetailsDto {

    @Pattern(regexp = "^[1-9]{1}[0-9]{2}[-]{0,1}[0-9]{3}$",message = "Please enter a valid 6 digits zip code")
    public String pinCode;

    @Pattern(regexp = "^[a-zA-Z]+",message = "Please enter valid location")
    public String locality;

    @Pattern(regexp = "^\\w{1,150}",message = "Please enter Address between 150 character")
    public String address;

    @Pattern(regexp = "^[a-zA-Z]+",message = "Please enter valid city name")
    public String city;

    @Pattern(regexp = "^[a-zA-Z]+",message = "Please enter valid landmark field")
    public String landmark;

    @Pattern(regexp = "^[a-zA-Z]+",message = "Please enter valid address type")
    public String addressType;


    public CustomerDetailsDto(String pinCode, String locality, String address, String city, String landmark, String addressType) {
        this.pinCode = pinCode;
        this.locality = locality;
        this.address = address;
        this.city = city;
        this.landmark = landmark;
        this.addressType=addressType;
    }
}
