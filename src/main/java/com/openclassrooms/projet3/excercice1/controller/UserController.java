package com.openclassrooms.projet3.excercice1.controller;

import com.openclassrooms.projet3.excercice1.dto.UserDto;
import com.openclassrooms.projet3.excercice1.dto.UserRegistrationDto;
import com.openclassrooms.projet3.excercice1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des utilisateurs.
 * Fournit les opérations CRUD pour les comptes utilisateurs.
 * 
 * @author Kévin Renault
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    /**
     * Crée un nouvel utilisateur.
     * 
     * @param userDto Les données de l'utilisateur à créer
     * @return Une réponse HTTP 201 avec les données de l'utilisateur créé
     */
    @Operation(summary = "Créer un utilisateur", description = "Crée un nouveau compte utilisateur")
    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserRegistrationDto userDto) {
        UserDto createdUser = userService.create(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Récupère un utilisateur par son identifiant.
     * 
     * @param id L'identifiant de l'utilisateur
     * @return Une réponse HTTP 200 avec les données de l'utilisateur
     */
    @Operation(summary = "Récupérer un utilisateur", description = "Récupère les informations d'un utilisateur par son ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Récupère un utilisateur par son adresse email.
     * 
     * @param email L'adresse email de l'utilisateur
     * @return Une réponse HTTP 200 avec les données de l'utilisateur
     */
    @Operation(summary = "Rechercher par email", description = "Récupère un utilisateur par son adresse email")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> findByEmail(@PathVariable String email) {
        UserDto user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    /**
     * Récupère la liste de tous les utilisateurs.
     * 
     * @return Une réponse HTTP 200 avec la liste des utilisateurs
     */
    @Operation(summary = "Lister les utilisateurs", description = "Récupère la liste complète des utilisateurs")
    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    /**
     * Met à jour les informations d'un utilisateur.
     * 
     * @param id      L'identifiant de l'utilisateur à modifier
     * @param userDto Les nouvelles données de l'utilisateur
     * @return Une réponse HTTP 200 avec les données de l'utilisateur mis à jour
     */
    @Operation(summary = "Modifier un utilisateur", description = "Met à jour les informations d'un utilisateur existant")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.update(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Supprime un utilisateur du système.
     * 
     * @param id L'identifiant de l'utilisateur à supprimer
     * @return Une réponse HTTP 204 (No Content)
     */
    @Operation(summary = "Supprimer un utilisateur", description = "Supprime définitivement un utilisateur du système")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
