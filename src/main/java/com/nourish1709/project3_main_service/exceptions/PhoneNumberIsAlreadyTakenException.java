package com.nourish1709.project3_main_service.exceptions;

public class PhoneNumberIsAlreadyTakenException extends RuntimeException{
    public PhoneNumberIsAlreadyTakenException(String message) {
        super(message);
    }
}
