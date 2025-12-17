package com.openclassrooms.projet3.excercice1.constants;

/**
 * Constantes des routes et endpoints de l'API.
 * 
 * @author Kévin Renault
 */
public final class ApiConstants {
    // Endpoints utilisés dans les tests unitaires (TU)
    public static final String RENTALS_BASE = "/api/rentals";
    public static final String RENTALS_ID = "/api/rentals/{id}";
    public static final String MESSAGES_BASE = "/api/messages";
    public static final String USER_ID = "/api/user/{id}";
    public static final String AUTH_ME = "/api/auth/me";

    private ApiConstants() {
        // Constructeur privé pour empêcher l'instanciation
    }

    /**
     * Routes publiques (accessibles sans authentification).
     */
    public static final String[] AUTH_PUBLIC_PATHS = {
            "/api/auth/register",
            "/api/auth/login"
    };
    public static final String MESSAGES_PUBLIC_PATHS = "/api/messages/**";
    public static final String UPLOADS_PUBLIC_PATHS = "/uploads/pictures/**";
    public static final String SWAGGER_PATHS = "/swagger-ui/**";
    public static final String API_DOCS_PATHS = "/v3/api-docs/**";
    public static final String SWAGGER_HTML_PATH = "/swagger-ui.html";
}
