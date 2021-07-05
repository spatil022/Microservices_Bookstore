package com.microusers.bookservice.exception;

public class UserException extends RuntimeException {

    public ExceptionType exceptionType;

    public UserException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public enum ExceptionType {
        EMAIL_NOT_FOUND("Enter Registered Email"),
        PASSWORD_INVALID("Invalid Password!!!Please Enter Correct Password"),

        INVALID_DATA("Please verify your email before proceeding"),
        USER_NOT_FOUND("user not found");

        public String error;

        ExceptionType(String errorMsg) {
            this.error = error;
        }

        }
    public UserException(String message) {
        super(message);

    }





}

