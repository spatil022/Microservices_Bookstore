package com.microusers.bookservice.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class UserDetailsModel {

    public UUID userId;
    public String fullName;
    private String phoneNumber;
    public String emailID;
    public String password;
    public boolean isVerified;
    public LocalDateTime createdAt = LocalDateTime.now();
    public LocalDateTime updatedAt;


    public UserDetailsModel(String fullName, String phoneNumber, String emailID, String password) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.emailID = emailID;
        this.password = password;
    }

    public UserDetailsModel(UserDetailsModel userDetailsModel){
        this.userId=userDetailsModel.getUserId();
        this.fullName=userDetailsModel.getFullName();
        this.phoneNumber=userDetailsModel.getPhoneNumber();
        this.emailID=userDetailsModel.getEmailID();
        this.password=userDetailsModel.getPassword();
        this.isVerified=userDetailsModel.isVerified();
        this.createdAt=userDetailsModel.getCreatedAt();
        this.updatedAt=userDetailsModel.getUpdatedAt();
    }


    public UserDetailsModel() {

    }
}