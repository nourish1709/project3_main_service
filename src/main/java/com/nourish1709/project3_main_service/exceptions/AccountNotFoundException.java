package com.nourish1709.project3_main_service.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        super("The account is not found!");
    }
}
