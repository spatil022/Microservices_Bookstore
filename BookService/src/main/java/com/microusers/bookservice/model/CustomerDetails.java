package com.microusers.bookservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microusers.bookservice.dto.CustomerDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetails implements Serializable {


    @Id
    @GeneratedValue(generator = "uuid2",strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    public UUID Costumerid;

    public String pinCode;
    public String locality;
    public String address;
    public String city;
    public String landmark;

    public String addressType;


    @JsonIgnore
    @Type(type="uuid-char")
    public UUID userId;

    @OneToMany(mappedBy = "customer")
    List<OderDetailsModel> orderDetailsList;

    public CustomerDetails(CustomerDetailsDto customerDetailsDto) {
        this.pinCode=customerDetailsDto.pinCode;
        this.locality=customerDetailsDto.locality;
        this.address=customerDetailsDto.city;
        this.landmark=customerDetailsDto.landmark;
        this.addressType=customerDetailsDto.addressType;
    }


    public CustomerDetails(String pinCode, String locality,String address, String city, String landmark, String addressType) {

        this.pinCode=pinCode;
        this.locality=locality;
        this.address=address;
        this.city=city;
        this.landmark=landmark;
        this.addressType=addressType;
    }

    public CustomerDetails(CustomerDetails customerDetails) {
        this.Costumerid=customerDetails.getCostumerid();
        this.addressType=customerDetails.getAddressType();
        this.address=customerDetails.getAddress();
        this.landmark=customerDetails.getLandmark();
        this.pinCode=customerDetails.getPinCode();
        this.locality=customerDetails.getLocality();
        this.city=customerDetails.getCity();
        this.locality=customerDetails.getLocality();
        this.userId=customerDetails.getUserId();

    }
}