package com.openclassrooms.projet3.excercice1.constants;

/**
 * Constantes liées à la sécurité et l'authentification JWT.
 * 
 * @author Kévin Renault
 */
public final class SecurityConstants {

    private SecurityConstants() {
        // Constructeur privé pour empêcher l'instanciation
    }

    /**
     * En-tête HTTP pour l'authentification.
     */
    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Préfixe du token Bearer.
     */
    public static final String BEARER_PREFIX = "Bearer ";

    /**
     * Longueur du préfixe Bearer (7 caractères : "Bearer ").
     */
    public static final int BEARER_PREFIX_LENGTH = 7;

    /**
     * Format du token JWT pour Swagger.
     */
    public static final String JWT_FORMAT = "JWT";

    /**
     * Nom du schéma de sécurité pour Swagger.
     */
    public static final String BEARER_AUTH_SCHEME = "bearerAuth";
}
