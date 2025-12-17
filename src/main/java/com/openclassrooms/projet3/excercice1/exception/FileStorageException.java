package com.openclassrooms.projet3.excercice1.exception;

import org.springframework.http.HttpStatus;

public class FileStorageException extends ApiException {

    /**
     * Constructeur de l'exception de requête invalide.
     * 
     * @param message Message décrivant la raison de l'invalidité
     */
    public FileStorageException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}