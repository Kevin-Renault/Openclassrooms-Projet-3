package com.openclassrooms.projet3.excercice1.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception levée lorsqu'une tentative de création d'une ressource échoue car
 * elle existe déjà.
 * Génère une réponse HTTP 409 (Conflict).
 * 
 * @author Kévin Renault
 */
public class ResourceAlreadyExistsException extends ApiException {

    /**
     * Constructeur avec détails de la ressource existante.
     * 
     * @param resourceName Nom du type de ressource (ex: "Utilisateur", "Location")
     * @param fieldName    Nom du champ en conflit (ex: "email")
     * @param fieldValue   Valeur en conflit
     */
    public ResourceAlreadyExistsException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s avec %s '%s' existe déjà", resourceName, fieldName, fieldValue),
                HttpStatus.CONFLICT);
    }

    /**
     * Constructeur avec message personnalisé.
     * 
     * @param message Message décrivant le conflit
     */
    public ResourceAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
