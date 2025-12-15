package com.openclassrooms.projet3.excercice1.controller;

import com.openclassrooms.projet3.excercice1.dto.MessageDto;
import com.openclassrooms.projet3.excercice1.dto.MessageResponse;
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
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody MessageDto messageDto) {
        messageService.create(messageDto);
        MessageResponse message = new MessageResponse("Message send with success");
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }
}
