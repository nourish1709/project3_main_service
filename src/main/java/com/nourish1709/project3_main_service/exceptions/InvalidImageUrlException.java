package com.nourish1709.project3_main_service.exceptions;

public class InvalidImageUrlException extends RuntimeException {
    public InvalidImageUrlException(String message) {
        super(message);
    }
}
