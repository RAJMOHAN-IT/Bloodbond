package com.randy.bloodbond.exception;

public class DonorNotFoundException extends RuntimeException {

    public DonorNotFoundException(String message) {
        super(message);
    }
}