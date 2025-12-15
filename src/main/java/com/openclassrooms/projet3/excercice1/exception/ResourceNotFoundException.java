package com.openclassrooms.projet3.excercice1.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception levée lorsqu'une ressource demandée n'est pas trouvée.
 * Génère une réponse HTTP 404 (Not Found).
 * 
 * @author Kévin Renault
 */
public class ResourceNotFoundException extends ApiException {

    /**
     * Constructeur avec détails de la ressource non trouvée.
     * 
     * @param resourceName Nom du type de ressource (ex: "Utilisateur", "Location")
     * @param fieldName    Nom du champ utilisé pour la recherche (ex: "id",
     *                     "email")
     * @param fieldValue   Valeur recherchée
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s non trouvé avec %s: %s", resourceName, fieldName, fieldValue),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Constructeur avec message personnalisé.
     * 
     * @param message Message décrivant la ressource non trouvée
     */
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
