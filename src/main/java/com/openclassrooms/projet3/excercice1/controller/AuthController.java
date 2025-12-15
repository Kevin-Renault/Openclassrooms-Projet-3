package com.openclassrooms.projet3.excercice1.controller;

import com.openclassrooms.projet3.excercice1.dto.AuthResponse;
import com.openclassrooms.projet3.excercice1.dto.LoginRequest;
import com.openclassrooms.projet3.excercice1.dto.RegisterRequest;
import com.openclassrooms.projet3.excercice1.dto.UserDto;
import com.openclassrooms.projet3.excercice1.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour la gestion de l'authentification.
 * Gère l'inscription, la connexion et la récupération des informations de
 * l'utilisateur connecté.
 * 
 * @author Kévin Renault
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Enregistre un nouvel utilisateur dans le système.
     * Crée un compte et retourne un token JWT pour l'authentification.
     * 
     * @param request Les données d'inscription (nom, email, mot de passe)
     * @return Une réponse HTTP 201 avec le token JWT et les données utilisateur
     */
    @Operation(summary = "Enregistrer un nouvel utilisateur", description = "Crée un nouveau compte utilisateur et retourne un token JWT")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Authentifie un utilisateur avec ses identifiants.
     * Génère un nouveau token JWT en cas de succès.
     * 
     * @param request Les identifiants de connexion (email et mot de passe)
     * @return Une réponse HTTP 200 avec le token JWT et les données utilisateur
     */
    @Operation(summary = "Connexion utilisateur", description = "Authentifie un utilisateur et retourne un token JWT")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Récupère les informations de l'utilisateur actuellement connecté.
     * Utilise le token JWT pour identifier l'utilisateur.
     * 
     * @param authentication L'objet d'authentification contenant les informations
     *                       du token
     * @return Une réponse HTTP 200 avec les données de l'utilisateur
     */
    @Operation(summary = "Utilisateur courant", description = "Récupère les informations de l'utilisateur authentifié")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        UserDto user = authService.getCurrentUser(authentication);
        return ResponseEntity.ok(user);
    }
}
