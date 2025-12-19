package com.openclassrooms.projet3.excercice1.controller;

import com.openclassrooms.projet3.excercice1.constants.SecurityConstants;
import com.openclassrooms.projet3.excercice1.dto.ErrorResponse;
import com.openclassrooms.projet3.excercice1.dto.RentalDto;
import com.openclassrooms.projet3.excercice1.dto.RentalResponse;
import com.openclassrooms.projet3.excercice1.service.RentalService;
import com.openclassrooms.projet3.excercice1.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des locations immobilières.
 * Fournit les opérations CRUD pour les annonces de location.
 * 
 * @author Kévin Renault
 */
@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@SecurityRequirement(name = SecurityConstants.BEARER_AUTH_SCHEME)
public class RentalController {

    private final RentalService rentalService;
    private final AuthService authService;

    /**
     * Crée une nouvelle annonce de location.
     * Gère également l'upload de l'image associée.
     * 
     * @param name           Nom de la location
     * @param surface        Surface en mètres carrés
     * @param price          Prix de la location
     * @param picture        Image de la location (optionnel)
     * @param description    Description de la location (optionnel)
     * @param authentication L'authentification de l'utilisateur connecté
     * @return Une réponse HTTP 201 avec les données de la location créée
     */
    @Validated
    @Operation(summary = "Créer une location", description = "Crée une nouvelle annonce de location avec upload d'image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location créée avec succès", content = @Content(schema = @Schema(implementation = RentalDto.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Non authentifié", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RentalDto> create(
            @RequestParam("name") String name,
            @RequestParam("surface") @Positive Integer surface,
            @RequestParam("price") @Positive Integer price,
            @RequestParam(value = "picture", required = false) MultipartFile picture,
            @RequestParam(value = "description", required = false) String description,
            Authentication authentication) {

        // Récupérer l'utilisateur connecté depuis le token JWT
        Long ownerId = authService.getCurrentUser(authentication).getId();

        RentalDto rentalDto = new RentalDto();
        rentalDto.setName(name);
        rentalDto.setSurface(surface);
        rentalDto.setPrice(price);
        rentalDto.setDescription(description);
        rentalDto.setOwnerId(ownerId);

        RentalDto createdRental = rentalService.create(rentalDto, picture);
        return ResponseEntity.status(HttpStatus.OK).body(createdRental);
    }

    /**
     * Récupère une location par son identifiant.
     * 
     * @param id L'identifiant de la location
     * @return Une réponse HTTP 200 avec les données de la location
     */
    @Operation(summary = "Récupérer une location", description = "Récupère les détails d'une location par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location trouvée", content = @Content(schema = @Schema(implementation = RentalDto.class))),
            @ApiResponse(responseCode = "404", description = "Location non trouvée", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<RentalDto> findById(@PathVariable Long id) {
        RentalDto rental = rentalService.findById(id);
        return ResponseEntity.ok(rental);
    }

    /**
     * Récupère la liste de toutes les locations.
     * 
     * @return Une réponse HTTP 200 avec la liste des locations
     */
    @Operation(summary = "Lister les locations", description = "Récupère la liste complète de toutes les locations disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des locations récupérée", content = @Content(schema = @Schema(implementation = RentalResponse.class)))
    })
    @GetMapping
    public ResponseEntity<RentalResponse> findAll() {
        List<RentalDto> rentals = rentalService.findAll();
        return ResponseEntity.ok(new RentalResponse(rentals));
    }

    /**
     * Met à jour les informations d'une location.
     * 
     * @param id          L'identifiant de la location à modifier
     * @param name        Nom de la location
     * @param surface     Surface en mètres carrés
     * @param price       Prix de la location
     * @param picture     Image de la location (optionnel)
     * @param description Description de la location (optionnel)
     * @return Une réponse HTTP 200 avec les données de la location mise à jour
     */
    @Validated
    @Operation(summary = "Modifier une location", description = "Met à jour les informations d'une location existante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location modifiée avec succès", content = @Content(schema = @Schema(implementation = RentalDto.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Location non trouvée", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RentalDto> update(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("surface") @Positive Integer surface,
            @RequestParam("price") @Positive Integer price,
            @RequestParam(value = "description", required = false) String description) {

        RentalDto rentalDto = new RentalDto();
        rentalDto.setName(name);
        rentalDto.setSurface(surface);
        rentalDto.setPrice(price);
        rentalDto.setDescription(description);

        RentalDto updatedRental = rentalService.update(id, rentalDto);
        return ResponseEntity.ok(updatedRental);
    }
}
