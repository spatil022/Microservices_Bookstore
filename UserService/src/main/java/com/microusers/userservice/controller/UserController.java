package com.microusers.userservice.controller;

import com.microusers.userservice.dto.ResponseDto;
import com.microusers.userservice.dto.ResponseLoginDTO;
import com.microusers.userservice.dto.UserDetailsDto;
import com.microusers.userservice.dto.UserLoginDto;
import com.microusers.userservice.model.UserDetailsModel;
import com.microusers.userservice.services.IUserService;
import com.microusers.userservice.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/user")
@ComponentScan
@EnableAutoConfiguration
public class UserController {
    @Autowired
    IUserService userService;


    Token jwtToken = new Token();

    @GetMapping("/welcome")
    public String welcome(){
        return "Hello in Online Book Store";
    }



    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody @Valid UserDetailsDto userDetails, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<ResponseDto>(new ResponseDto(bindingResult.getAllErrors().get(0).
                    getDefaultMessage(),"100",null),
                    HttpStatus.BAD_REQUEST);
        }
        UserDetailsModel userDetailsModel = userService.addUser(userDetails);
        return new ResponseEntity (new ResponseDto("USER ADDED SUCCESSFULLY: ",
                "200",userDetailsModel),
                HttpStatus.CREATED);
    }

    @GetMapping("/verify/email/{tokenId}")
    public ResponseEntity verifyEmail(@PathVariable String  tokenId ){
        System.out.println("the token id from responseEntity is : "+tokenId);
        userService.verifyEmail(tokenId);
        return new ResponseEntity ("EMAIL VERIFIED",HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDTO> login(@RequestBody @Valid UserLoginDto userLoginDTO, BindingResult bindingResult, HttpServletResponse httpServletResponse) {
        if (bindingResult.hasErrors()) {

            return new ResponseEntity<ResponseLoginDTO>(new ResponseLoginDTO(bindingResult.getAllErrors().get(0).
                    getDefaultMessage(),"100",null,null),
                    HttpStatus.BAD_REQUEST);

        }
        UserDetailsModel userLogin = userService.userLogin(userLoginDTO);
        System.out.println("user login details "+userLogin);
        String tokenString = jwtToken.generateLoginToken(userLogin);
        System.out.println("token "+tokenString);
        httpServletResponse.setHeader("Authorization", tokenString);
        return new ResponseEntity (new ResponseDto("LOGIN SUCCESSFUL",
                "200",tokenString,userLogin.getFullName()),
                HttpStatus.OK);
    }

    @PostMapping("/forget/password")
    public ResponseEntity<ResponseDto> getResetPassword(@RequestParam("emailID") String emailID) throws MessagingException {
        String link = userService.resetPasswordLink(emailID);
        ResponseDto response = new ResponseDto(link);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/reset/password/")
    public ResponseEntity<ResponseDto> resetPassword(@RequestParam(name = "password") String password,@RequestParam(value = "token",defaultValue = "") String urlToken){
        String resetPassword = userService.resetPassword(password,urlToken);
        ResponseDto response = new ResponseDto(resetPassword);
        return new ResponseEntity(response,HttpStatus.OK);
    }

    @PostMapping("/resend/mail")
    public ResponseEntity<ResponseDto> resendMail(@RequestParam("emailID") String emailID) throws MessagingException {
        String link = userService.resetPasswordLink(emailID);
        ResponseDto response = new ResponseDto(link);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/getDetailsOfUser")
    public ResponseEntity<List<UserDetailsModel>> getUserDetails(@RequestHeader(value = "token", required = false) String token){

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserInformation(token));

    }
    @GetMapping("/getuser")
    public UserDetailsModel getUserDetailsToken(@RequestParam(name = "userEmailToken") String Token){

        UserDetailsModel userDetailsModel=userService.setUserDetails(Token);
        return userDetailsModel;


    }

}
