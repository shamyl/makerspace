package com.shf.makerspace.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class GenericException extends RuntimeException {

    private final HttpStatus status;

    public GenericException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        if (!message.equalsIgnoreCase("Could not parse claims")) {
            log.error(message);
            printStackTrace();
        }

    }

    public HttpStatus getStatus() {
        return status;
    }

}
