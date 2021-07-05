package com.microusers.userservice.services;

import com.microusers.userservice.dto.UserDetailsDto;
import com.microusers.userservice.dto.UserLoginDto;
import com.microusers.userservice.model.UserDetailsModel;

import javax.mail.MessagingException;
import java.util.List;

public interface IUserService {
    UserDetailsModel addUser(UserDetailsDto userDetails);

    void verifyEmail(String tokenId);

    UserDetailsModel userLogin(UserLoginDto userLoginDto);

    String resetPasswordLink(String email) throws MessagingException;

    String resetPassword(String password, String urlToken);

    List<UserDetailsModel> getUserInformation(String token);

    UserDetailsModel setUserDetails(String token);
}
