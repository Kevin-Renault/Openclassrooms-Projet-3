package com.openclassrooms.projet3.excercice1.dto;

import com.openclassrooms.projet3.excercice1.config.ValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) représentant une requête d'inscription.
 * Contient toutes les informations nécessaires pour créer un nouveau compte
 * utilisateur.
 * 
 * @author Kévin Renault
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    /**
     * Nom complet de l'utilisateur.
     */
    @NotBlank
    @Size(max = ValidationConstants.NAME_MAX_SIZE)
    private String name;

    /**
     * Adresse email de l'utilisateur (doit être unique).
     */
    @NotBlank
    @Email
    @Size(max = ValidationConstants.EMAIL_MAX_SIZE)
    private String email;

    /**
     * Mot de passe de l'utilisateur (minimum 8 caractères).
     */
    @NotBlank
    @Size(min = ValidationConstants.PASSWORD_MIN_SIZE)
    private String password;
}
