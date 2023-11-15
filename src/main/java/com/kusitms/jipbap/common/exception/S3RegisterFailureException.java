package com.kusitms.jipbap.common.exception;

public class S3RegisterFailureException extends RuntimeException{
    public S3RegisterFailureException(String message) {
        super(message);
    }
}
