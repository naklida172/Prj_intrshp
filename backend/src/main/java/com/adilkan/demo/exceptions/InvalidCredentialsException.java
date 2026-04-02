package com.adilkan.demo.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Incorrect login or password");
    }
}
