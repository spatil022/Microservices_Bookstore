package com.microusers.userservice.exception;

public class BookStoreException extends RuntimeException {

    public ExceptionTypes exceptionTypes;

    public BookStoreException(ExceptionTypes exceptionTypes) {
        this.exceptionTypes = exceptionTypes;
    }


    public enum ExceptionTypes {
        USER_ALREADY_PRESENT("user Already present"),
        USER_NOT_FOUND("user not found"),
        INVALID_USER_ID("user id you have given is incorrect"),
        USER_NOT_PRESENT("user is not present in database"),
        BOOK_AlREADY_PRESENT("book is already added in database"),
        BOOK_NOT_FOUND("book is not found"),
        CART_NOT_PRESENT("invalid cart id"),
        NO_BOOKS_FOUND("no books available"),
        BOOK_ALREADY_PRESENT_IN_CART("book already present in cart"),
        WISH_LIST_IS_NOT_PRESENT("User not present for  wishList");

        public String errorMsg;

        ExceptionTypes(String errorMsg) {
            this.errorMsg = errorMsg;
        }

    }

    public BookStoreException(String message) { super(message); }
}
