package com.GKPS.Config;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException{
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND");
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s tidak ditemukan dengan %s '%s'", resourceName, fieldName, fieldValue), HttpStatus.NOT_FOUND, "Resources_Not_Found");
    }
}
