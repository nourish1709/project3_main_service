package com.nourish1709.project3_main_service.exceptions;

public class InvalidSkuIdException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Sku with id skuId wasn't found";

    public InvalidSkuIdException(Long id) {
        super(ERROR_MESSAGE.replaceFirst("skuId", id.toString()));
    }
}
