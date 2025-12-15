package com.openclassrooms.projet3.excercice1.controller;

import com.openclassrooms.projet3.excercice1.dto.MessageDto;
import com.openclassrooms.projet3.excercice1.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des messages.
 * Permet aux utilisateurs d'envoyer et de consulter des messages concernant les
 * locations.
 * 
 * @author Kévin Renault
 */
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class MessageController {

    private final MessageService messageService;

    /**
     * Crée un nouveau message.
     * 
     * @param messageDto Les données du message à créer
     * @return Une réponse HTTP 201 avec les données du message créé
     */
    @Operation(summary = "Envoyer un message", description = "Crée un nouveau message concernant une location")
    @PostMapping
    public ResponseEntity<MessageDto> create(@Valid @RequestBody MessageDto messageDto) {
        MessageDto createdMessage = messageService.create(messageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);
    }

    /**
     * Récupère un message par son identifiant.
     * 
     * @param id L'identifiant du message
     * @return Une réponse HTTP 200 avec les données du message
     */
    @Operation(summary = "Récupérer un message", description = "Récupère les détails d'un message par son ID")
    @GetMapping("/{id}")
    public ResponseEntity<MessageDto> findById(@PathVariable Long id) {
        MessageDto message = messageService.findById(id);
        return ResponseEntity.ok(message);
    }

    /**
     * Récupère la liste de tous les messages.
     * 
     * @return Une réponse HTTP 200 avec la liste des messages
     */
    @Operation(summary = "Lister les messages", description = "Récupère la liste complète de tous les messages")
    @GetMapping
    public ResponseEntity<List<MessageDto>> findAll() {
        List<MessageDto> messages = messageService.findAll();
        return ResponseEntity.ok(messages);
    }

    /**
     * Récupère tous les messages associés à une location spécifique.
     * 
     * @param rentalId L'identifiant de la location
     * @return Une réponse HTTP 200 avec la liste des messages de la location
     */
    @Operation(summary = "Messages par location", description = "Récupère tous les messages concernant une location spécifique")
    @GetMapping("/rental/{rentalId}")
    public ResponseEntity<List<MessageDto>> findByRentalId(@PathVariable Long rentalId) {
        List<MessageDto> messages = messageService.findByRentalId(rentalId);
        return ResponseEntity.ok(messages);
    }

    /**
     * Récupère tous les messages envoyés par un utilisateur spécifique.
     * 
     * @param userId L'identifiant de l'utilisateur
     * @return Une réponse HTTP 200 avec la liste des messages de l'utilisateur
     */
    @Operation(summary = "Messages par utilisateur", description = "Récupère tous les messages envoyés par un utilisateur")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MessageDto>> findByUserId(@PathVariable Long userId) {
        List<MessageDto> messages = messageService.findByUserId(userId);
        return ResponseEntity.ok(messages);
    }

    /**
     * Met à jour le contenu d'un message.
     * 
     * @param id         L'identifiant du message à modifier
     * @param messageDto Les nouvelles données du message
     * @return Une réponse HTTP 200 avec les données du message mis à jour
     */
    @Operation(summary = "Modifier un message", description = "Met à jour le contenu d'un message existant")
    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> update(@PathVariable Long id, @Valid @RequestBody MessageDto messageDto) {
        MessageDto updatedMessage = messageService.update(id, messageDto);
        return ResponseEntity.ok(updatedMessage);
    }

    /**
     * Supprime un message du système.
     * 
     * @param id L'identifiant du message à supprimer
     * @return Une réponse HTTP 204 (No Content)
     */
    @Operation(summary = "Supprimer un message", description = "Supprime définitivement un message du système")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        messageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
