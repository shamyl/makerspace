package com.shf.makerspace.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<GenericExceptionErrorResponse> handleGenericException(GenericException ex, WebRequest request) {
        GenericExceptionErrorResponse errorResponse = new GenericExceptionErrorResponse(
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""), // Extracting path from WebRequest
                ex.getStatus().value(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
