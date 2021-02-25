package com.nourish1709.project3_main_service.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
        super("The category is not found!");
    }
}
