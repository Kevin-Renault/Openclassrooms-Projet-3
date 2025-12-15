package com.openclassrooms.projet3.excercice1.dto;

import com.openclassrooms.projet3.excercice1.config.ValidationConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) représentant un message.
 * Un message est envoyé par un utilisateur concernant une location spécifique.
 * 
 * @author Kévin Renault
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    /**
     * Identifiant unique du message.
     */
    private Long id;

    /**
     * Identifiant de la location concernée par le message.
     */
    @NotNull
    @Positive
    private Long rentalId;

    /**
     * Identifiant de l'utilisateur qui envoie le message.
     */
    @NotNull
    @Positive
    private Long userId;

    /**
     * Contenu textuel du message.
     */
    @NotBlank
    @Size(max = ValidationConstants.MESSAGE_MAX_SIZE)
    private String message;

    /**
     * Date et heure de création du message.
     */
    @PastOrPresent
    private LocalDateTime createdAt;

    /**
     * Date et heure de la dernière mise à jour du message.
     */
    @PastOrPresent
    private LocalDateTime updatedAt;
}
