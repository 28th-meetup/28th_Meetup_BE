package com.kusitms.jipbap.auth.exception;

public class UsernameExistsException extends RuntimeException{
    public UsernameExistsException(String message) {
        super(message);
    }
}
