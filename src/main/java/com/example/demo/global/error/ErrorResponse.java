package com.example.demo.global.error;

import lombok.Getter;

@Getter
public enum ErrorResponse {
    ;
    private final String message;
    private final int status;

    ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
