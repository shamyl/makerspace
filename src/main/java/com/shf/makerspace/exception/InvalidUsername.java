package com.shf.makerspace.exception;


public class InvalidUsername extends RuntimeException {
    private static final long serialVersionUID = -6549403557869364123L;

    public InvalidUsername(String message) {
        super(message);
    }
}
