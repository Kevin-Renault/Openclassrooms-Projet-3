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
     * Routes publiques (accessibles sans authentification).
     */
    public static final String AUTH_PUBLIC_PATHS = "/api/auth/**";
    public static final String UPLOADS_PUBLIC_PATHS = "/uploads/pictures/**";
    public static final String SWAGGER_PATHS = "/swagger-ui/**";
    public static final String API_DOCS_PATHS = "/v3/api-docs/**";
    public static final String SWAGGER_HTML_PATH = "/swagger-ui.html";
}
