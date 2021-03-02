package com.nourish1709.project3_main_service.exceptions;

public class IncorrectCredentialsException extends RuntimeException {
    private static final String ERROR_MESSAGE = "The username or password you've provided is incorrect";

    public IncorrectCredentialsException() {
        super(ERROR_MESSAGE);
    }
}