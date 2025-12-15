package com.openclassrooms.projet3.excercice1.dto;

import com.openclassrooms.projet3.excercice1.config.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) pour l'inscription d'un nouvel utilisateur.
 * Étend UserDto et ajoute le champ password nécessaire lors de la création.
 * 
 * @author Kévin Renault
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRegistrationDto extends UserDto {

    /**
     * Mot de passe de l'utilisateur (minimum 8 caractères).
     */
    @NotBlank
    @Size(min = ValidationConstants.PASSWORD_MIN_SIZE)
    private String password;
}
