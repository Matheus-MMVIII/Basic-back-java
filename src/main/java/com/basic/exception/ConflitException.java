package com.basic.exception;

public class ConflitException extends ApiException {
    public ConflitException(String message) {
        super(409, message);
    }
}