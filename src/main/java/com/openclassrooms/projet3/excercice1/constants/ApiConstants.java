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

    public static final String API_RENTALS_URL_BASE = "/api/rentals";
    public static final String API_RENTALS_URL_ID = API_RENTALS_URL_BASE + "/{id}";
    public static final String API_MESSAGES_URL_BASE = "/api/messages";
    public static final String API_USER_URL_BASE = "/api/user";
    public static final String API_USER_URL_ID = API_USER_URL_BASE + "/{id}";
    public static final String API_AUTH_URL_BASE = "/api/auth";
    public static final String API_AUTH_URL_ME = API_AUTH_URL_BASE + "/me";
    public static final String API_AUTH_URL_REGISTER = API_AUTH_URL_BASE + "/register";
    public static final String API_AUTH_URL_LOGIN = API_AUTH_URL_BASE + "/login";

    public static final String MESSAGES_PUBLIC_PATHS = API_MESSAGES_URL_BASE + "/**";
    public static final String UPLOADS_PUBLIC_PATHS = "/uploads/pictures/**";
    public static final String SWAGGER_PATHS = "/swagger-ui/**";
    public static final String API_DOCS_PATHS = "/v3/api-docs/**";
    public static final String SWAGGER_HTML_PATH = "/swagger-ui.html";
}
