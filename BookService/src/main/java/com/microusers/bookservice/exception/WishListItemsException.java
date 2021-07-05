package com.microusers.bookservice.exception;


import lombok.Getter;

@Getter
public class WishListItemsException extends RuntimeException {
    public WishListItemsException(String message) {
        super(message);
    }
}
