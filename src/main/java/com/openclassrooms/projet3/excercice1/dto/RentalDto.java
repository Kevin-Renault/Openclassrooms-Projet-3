package com.openclassrooms.projet3.excercice1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.projet3.excercice1.config.ValidationConstants;
import com.openclassrooms.projet3.excercice1.constants.FormatConstants;
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
 * DTO (Data Transfer Object) représentant une location immobilière.
 * Contient toutes les informations d'une annonce de location.
 * 
 * @author Kévin Renault
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {

    /**
     * Identifiant unique de la location.
     */
    private Long id;

    /**
     * Nom ou titre de la location.
     */
    @NotBlank
    @Size(max = ValidationConstants.NAME_MAX_SIZE)
    private String name;

    /**
     * Surface de la location en mètres carrés.
     */
    @NotNull
    @Positive
    private Integer surface;

    /**
     * Prix de la location.
     */
    @NotNull
    @Positive
    private Integer price;

    /**
     * URL de l'image de la location.
     */
    @Size(max = ValidationConstants.PICTURE_MAX_SIZE)
    private String picture;

    /**
     * Description détaillée de la location.
     */
    @Size(max = ValidationConstants.DESCRIPTION_MAX_SIZE)
    private String description;

    /**
     * Identifiant du propriétaire de la location.
     */
    @NotNull
    @Positive
    @JsonProperty("owner_id")
    private Long ownerId;

    /**
     * Date et heure de création de l'annonce.
     */
    @PastOrPresent
    @JsonProperty("created_at")
    @JsonFormat(pattern = FormatConstants.DISPLAY_DATE_FORMAT)
    private LocalDateTime createdAt;

    /**
     * Date et heure de la dernière mise à jour de l'annonce.
     */
    @PastOrPresent
    @JsonProperty("updated_at")
    @JsonFormat(pattern = FormatConstants.DISPLAY_DATE_FORMAT)
    private LocalDateTime updatedAt;
}
