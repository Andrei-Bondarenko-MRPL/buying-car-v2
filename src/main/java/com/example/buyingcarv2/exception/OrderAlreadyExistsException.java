package com.example.buyingcarv2.exception;

public class OrderAlreadyExistsException extends RuntimeException {

    public OrderAlreadyExistsException (String message) {
        super(message);
    }
}
