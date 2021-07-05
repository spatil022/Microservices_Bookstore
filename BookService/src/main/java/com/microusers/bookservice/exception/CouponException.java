package com.microusers.bookservice.exception;

public class CouponException extends RuntimeException {

    public ExceptionType exceptionType;

    public CouponException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public enum ExceptionType {
        COUPON_NOT_AVAILABLE("coupon not Avilable"),
        COUPON_ALREADY_PRESENT("coupon already present");

        public String error;

        ExceptionType(String errorMsg) {
            this.error = errorMsg;
        }

    }

    public CouponException(String message) {
        super(message);

    }
}
