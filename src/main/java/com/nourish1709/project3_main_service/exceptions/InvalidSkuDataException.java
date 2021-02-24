package com.nourish1709.project3_main_service.exceptions;

public class InvalidSkuDataException extends RuntimeException {
    public InvalidSkuDataException(String errorMessage) {
        super(errorMessage);
    }
}
