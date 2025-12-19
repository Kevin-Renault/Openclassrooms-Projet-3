package com.openclassrooms.projet3.excercice1.controller;

import com.openclassrooms.projet3.excercice1.constants.SecurityConstants;
import com.openclassrooms.projet3.excercice1.dto.ErrorResponse;
import com.openclassrooms.projet3.excercice1.dto.UserDto;
import com.openclassrooms.projet3.excercice1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour la gestion des utilisateurs.
 * Fournit les opérations CRUD pour les comptes utilisateurs.
 * 
 * @author Kévin Renault
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@SecurityRequirement(name = SecurityConstants.BEARER_AUTH_SCHEME)
public class UserController {

    private final UserService userService;

    /**
     * Récupère un utilisateur par son identifiant.
     * 
     * @param id L'identifiant de l'utilisateur
     * @return Une réponse HTTP 200 avec les données de l'utilisateur
     */
    @Operation(summary = "Récupérer un utilisateur", description = "Récupère les informations d'un utilisateur par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé", content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
}
