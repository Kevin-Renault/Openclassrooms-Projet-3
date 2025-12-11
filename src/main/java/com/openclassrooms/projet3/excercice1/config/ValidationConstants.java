package com.openclassrooms.projet3.excercice1.config;

public final class ValidationConstants {

    private ValidationConstants() {
        // Classe utilitaire, pas d'instanciation
    }

    // Tailles maximales
    public static final int NAME_MAX_SIZE = 255;
    public static final int EMAIL_MAX_SIZE = 255;
    public static final int PASSWORD_MIN_SIZE = 8;
    public static final int PICTURE_MAX_SIZE = 1000;
    public static final int DESCRIPTION_MAX_SIZE = 2000;
    public static final int MESSAGE_MAX_SIZE = 2000;
}
