package com.openclassrooms.projet3.excercice1.exception;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends ApiException {

    public ResourceAlreadyExistsException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s avec %s '%s' existe déjà", resourceName, fieldName, fieldValue),
                HttpStatus.CONFLICT);
    }

    public ResourceAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
