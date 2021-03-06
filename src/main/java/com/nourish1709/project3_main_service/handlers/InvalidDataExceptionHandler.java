package com.nourish1709.project3_main_service.handlers;

import com.nourish1709.project3_main_service.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class InvalidDataExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(
            value = {
                    InvalidAgeException.class,
                    InvalidImageUrlException.class,
                    InvalidNameException.class,
                    InvalidPhoneException.class,
                    InvalidSkuDataException.class
            }
    )
    ResponseEntity<Object> handleConflict(
            RuntimeException exception,
            WebRequest request
    ) {
        return handleExceptionInternal(exception, exception.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}