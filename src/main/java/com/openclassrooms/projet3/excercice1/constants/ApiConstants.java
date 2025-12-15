package com.openclassrooms.projet3.excercice1.constants;

/**
 * Constantes des routes et endpoints de l'API.
 * 
 * @author Kévin Renault
 */
public final class ApiConstants {

    private ApiConstants() {
        // Constructeur privé pour empêcher l'instanciation
    }

    /**
     * Préfixe de base pour toutes les routes de l'API.
     */
    public static final String API_PREFIX = "/api";

    /**
     * Routes d'authentification.
     */
    public static final String AUTH_BASE_PATH = API_PREFIX + "/auth";
    public static final String AUTH_REGISTER_PATH = "/register";
    public static final String AUTH_LOGIN_PATH = "/login";
    public static final String AUTH_ME_PATH = "/me";

    /**
     * Routes des utilisateurs.
     */
    public static final String USER_BASE_PATH = API_PREFIX + "/user";

    /**
     * Routes des locations.
     */
    public static final String RENTALS_BASE_PATH = API_PREFIX + "/rentals";

    /**
     * Routes des messages.
     */
    public static final String MESSAGES_BASE_PATH = API_PREFIX + "/messages";

    /**
     * Routes publiques (accessibles sans authentification).
     */
    public static final String AUTH_PUBLIC_PATHS = AUTH_BASE_PATH + "/**";
    public static final String UPLOADS_PUBLIC_PATHS = "/uploads/pictures/**";
    public static final String SWAGGER_PATHS = "/swagger-ui/**";
    public static final String API_DOCS_PATHS = "/v3/api-docs/**";
    public static final String SWAGGER_HTML_PATH = "/swagger-ui.html";
}
