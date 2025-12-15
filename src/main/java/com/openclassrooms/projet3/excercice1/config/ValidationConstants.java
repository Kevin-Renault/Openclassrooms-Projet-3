package com.openclassrooms.projet3.excercice1.config;

/**
 * Classe utilitaire contenant les constantes de validation.
 * Définit les tailles maximales et minimales pour les champs de l'application.
 * 
 * @author Kévin Renault
 */
public final class ValidationConstants {

    /**
     * Constructeur privé pour empêcher l'instanciation.
     */
    private ValidationConstants() {
        // Classe utilitaire, pas d'instanciation
    }

    /**
     * Taille maximale pour les noms (utilisateur, location, etc.).
     */
    public static final int NAME_MAX_SIZE = 255;

    /**
     * Taille maximale pour les adresses email.
     */
    public static final int EMAIL_MAX_SIZE = 255;

    /**
     * Taille minimale pour les mots de passe.
     */
    public static final int PASSWORD_MIN_SIZE = 8;

    /**
     * Taille maximale pour les URLs d'images.
     */
    public static final int PICTURE_MAX_SIZE = 1000;

    /**
     * Taille maximale pour les descriptions de location.
     */
    public static final int DESCRIPTION_MAX_SIZE = 2000;

    /**
     * Taille maximale pour les messages.
     */
    public static final int MESSAGE_MAX_SIZE = 2000;
}
