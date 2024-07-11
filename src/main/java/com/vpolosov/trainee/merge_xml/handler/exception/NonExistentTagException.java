package com.vpolosov.trainee.merge_xml.handler.exception;

public class NonExistentTagException extends RuntimeException {
    public NonExistentTagException(String message) {
        super(message);
    }
}
