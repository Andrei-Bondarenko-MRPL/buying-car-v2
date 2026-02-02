package com.example.buyingcarv2.exception;

public class NoSuchOrderExistsException extends RuntimeException {

    public NoSuchOrderExistsException(String message) {
        super(message);
    }
}
