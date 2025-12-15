package com.openclassrooms.projet3.excercice1.dto;

import com.openclassrooms.projet3.excercice1.config.ValidationConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) représentant les données d'un utilisateur.
 * Utilisé pour les échanges entre le client et l'API.
 * Ne contient pas le mot de passe pour des raisons de sécurité.
 * 
 * @author Kévin Renault
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    /**
     * Identifiant unique de l'utilisateur.
     */
    private Long id;

    /**
     * Nom complet de l'utilisateur.
     */
    @NotBlank
    @Size(max = ValidationConstants.NAME_MAX_SIZE)
    private String name;

    /**
     * Adresse email de l'utilisateur.
     */
    @NotBlank
    @Email
    @Size(max = ValidationConstants.EMAIL_MAX_SIZE)
    private String email;

    /**
     * Date et heure de création du compte.
     */
    @PastOrPresent
    private LocalDateTime createdAt;

    /**
     * Date et heure de la dernière mise à jour.
     */
    @PastOrPresent
    private LocalDateTime updatedAt;
}
