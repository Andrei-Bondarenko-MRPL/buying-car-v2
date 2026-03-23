package com.example.buyingcarv2.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private String localDateTime;

    public ErrorResponse(String message, String localDateTime) {
        this.message = message;
        this.localDateTime = localDateTime;
    }
}
