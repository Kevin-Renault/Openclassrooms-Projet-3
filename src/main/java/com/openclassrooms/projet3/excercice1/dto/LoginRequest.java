package com.openclassrooms.projet3.excercice1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) représentant une requête de connexion.
 * Contient les identifiants nécessaires pour l'authentification.
 * 
 * @author Kévin Renault
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    /**
     * Adresse email de l'utilisateur.
     */
    @NotBlank
    @Email
    private String email;

    /**
     * Mot de passe de l'utilisateur.
     */
    @NotBlank
    private String password;
}
