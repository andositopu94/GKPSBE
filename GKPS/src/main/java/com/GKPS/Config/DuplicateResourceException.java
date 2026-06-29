package com.GKPS.Config;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends ApiException{
    public DuplicateResourceException(String message) {
        super(message, HttpStatus.CONFLICT, "DUPLICATE_RESOURCE");
    }

    public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s sudah ada dengan %s '%s'", resourceName, fieldName, fieldValue), HttpStatus.CONFLICT, "DUPLICATE_RESOURCE");
    }
}
