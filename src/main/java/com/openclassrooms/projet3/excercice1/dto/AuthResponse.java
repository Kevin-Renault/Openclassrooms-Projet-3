package com.openclassrooms.projet3.excercice1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) représentant la réponse d'authentification.
 * Contient le token JWT et les informations de l'utilisateur connecté.
 * 
 * @author Kévin Renault
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    /**
     * Token JWT pour l'authentification.
     */
    private String token;

    /**
     * Type de token (Bearer par défaut).
     */
    private String type = "Bearer";

    /**
     * Données de l'utilisateur authentifié.
     */
    private UserDto user;

    /**
     * Constructeur avec token et user.
     * Le type est automatiquement défini à "Bearer".
     * 
     * @param token Token JWT généré
     * @param user  Données de l'utilisateur
     */
    public AuthResponse(String token, UserDto user) {
        this.token = token;
        this.user = user;
    }
}
