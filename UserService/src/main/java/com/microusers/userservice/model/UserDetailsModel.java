package com.microusers.userservice.model;

import com.microusers.userservice.dto.UserDetailsDto;
import com.microusers.userservice.dto.UserLoginDto;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class UserDetailsModel implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2",strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    public UUID userId;
    public String fullName;
    private String phoneNumber;
    public String emailID;
    public String password;
    public boolean isVerified;
    public LocalDateTime createdAt = LocalDateTime.now();
    public LocalDateTime updatedAt;




//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userDetails")
//    public List<CustomerDetails> customerDetails;
//
//
//
//    @OneToMany(mappedBy = "user")
//    public List<OderDetailsModel> orderDetails;








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


    public UserDetailsModel(UserDetailsDto userDetailsDto) {
        this.fullName = userDetailsDto.getFullName();
        this.phoneNumber = userDetailsDto.getPhoneNumber();
        this.emailID = userDetailsDto.getEmailID();
        this.password = userDetailsDto.getPassword();
    }

    public UserDetailsModel(UserLoginDto loginDto) {
        this.emailID = loginDto.emailID;
        this.password = loginDto.password;

    }

}
