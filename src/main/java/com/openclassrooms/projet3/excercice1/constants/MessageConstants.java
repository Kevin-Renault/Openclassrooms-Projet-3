package com.openclassrooms.projet3.excercice1.constants;

/**
 * Constantes pour les messages de succès et d'erreur de l'application.
 * 
 * @author Kévin Renault
 */
public final class MessageConstants {

    private MessageConstants() {
        // Constructeur privé pour empêcher l'instanciation
    }

    /**
     * Messages de succès.
     */
    public static final String RENTAL_CREATED_SUCCESS = "Rental created !";
    public static final String RENTAL_UPDATED_SUCCESS = "Rental updated !";
    public static final String MESSAGE_SENT_SUCCESS = "Message send with success";

    /**
     * Messages d'erreur génériques.
     */
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access";
}
