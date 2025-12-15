package com.openclassrooms.projet3.excercice1.constants;

/**
 * Constantes liées aux entités et aux champs de la base de données.
 * 
 * @author Kévin Renault
 */
public final class EntityConstants {

    private EntityConstants() {
        // Constructeur privé pour empêcher l'instanciation
    }

    /**
     * Noms des entités.
     */
    public static final String ENTITY_USER = "Utilisateur";
    public static final String ENTITY_RENTAL = "Location";
    public static final String ENTITY_MESSAGE = "Message";

    /**
     * Noms des champs.
     */
    public static final String FIELD_ID = "id";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_OWNER_ID = "owner_id";
    public static final String FIELD_RENTAL_ID = "rental_id";
    public static final String FIELD_USER_ID = "user_id";
}
