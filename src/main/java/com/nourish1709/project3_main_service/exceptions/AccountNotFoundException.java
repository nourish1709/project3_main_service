package com.nourish1709.project3_main_service.exceptions;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException() {
        super("Account is not found!");
    }
}
