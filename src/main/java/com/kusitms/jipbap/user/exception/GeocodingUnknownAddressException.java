package com.kusitms.jipbap.user.exception;

public class GeocodingUnknownAddressException extends RuntimeException {
    public GeocodingUnknownAddressException(String message) {
        super(message);
    }
}