package com.nourish1709.project3_main_service.exceptions;

public class InvalidNameException extends RuntimeException {
    public InvalidNameException(String description) {
        super(description);
    }
}
