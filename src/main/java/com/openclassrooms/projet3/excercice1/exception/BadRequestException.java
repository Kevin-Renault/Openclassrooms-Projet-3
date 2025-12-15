package com.openclassrooms.projet3.excercice1.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception levée lorsqu'une requête est mal formée ou contient des données
 * invalides.
 * Génère une réponse HTTP 400 (Bad Request).
 * 
 * @author Kévin Renault
 */
public class BadRequestException extends ApiException {

    /**
     * Constructeur de l'exception de requête invalide.
     * 
     * @param message Message décrivant la raison de l'invalidité
     */
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
