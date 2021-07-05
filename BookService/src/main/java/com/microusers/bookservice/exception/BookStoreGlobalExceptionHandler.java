package com.microusers.bookservice.exception;


import com.microusers.bookservice.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class BookStoreGlobalExceptionHandler {
    @ExceptionHandler(BookStoreException.class)
    public ResponseEntity<ResponseDto> handleAddressBookException(BookStoreException bookStoreException){
        log.error("Exception Occurred : " +bookStoreException.exceptionTypes.errorMsg);
        return new ResponseEntity<ResponseDto>(new ResponseDto(bookStoreException.exceptionTypes.errorMsg,
                                                              null,null),
                                                                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ResponseDto> userExceptionHandler(UserException userException) {
        log.error("Exception Occurred : " +userException.exceptionType.error);
        return new ResponseEntity<ResponseDto>(new ResponseDto(userException.exceptionType.error, null,null),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CouponException.class)
    public ResponseEntity<ResponseDto> CouponExceptionHandler(CouponException couponException) {
        log.error("Exception Occurred : " +couponException.exceptionType.error);
        return new ResponseEntity<ResponseDto>(new ResponseDto(couponException.exceptionType.error, "400",null),HttpStatus.BAD_REQUEST);
    }



}
