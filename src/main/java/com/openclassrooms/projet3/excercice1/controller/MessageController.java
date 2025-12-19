package com.openclassrooms.projet3.excercice1.controller;

import com.openclassrooms.projet3.excercice1.constants.ApiConstants;
import com.openclassrooms.projet3.excercice1.constants.MessageConstants;
import com.openclassrooms.projet3.excercice1.constants.SecurityConstants;
import com.openclassrooms.projet3.excercice1.dto.ErrorResponse;
import com.openclassrooms.projet3.excercice1.dto.MessageDto;
import com.openclassrooms.projet3.excercice1.dto.MessageResponse;
import com.openclassrooms.projet3.excercice1.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour la gestion des messages.
 * Permet aux utilisateurs d'envoyer et de consulter des messages concernant les
 * locations.
 * 
 * @author Kévin Renault
 */
@RestController
@RequestMapping(ApiConstants.MESSAGES_PUBLIC_PATHS)
@RequiredArgsConstructor
@SecurityRequirement(name = SecurityConstants.BEARER_AUTH_SCHEME)
public class MessageController {

    private final MessageService messageService;

    /**
     * Crée un nouveau message.
     * 
     * @param messageDto Les données du message à créer
     * @return Une réponse HTTP 201 avec les données du message créé
     */
    @Operation(summary = "Envoyer un message", description = "Crée un nouveau message concernant une location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message envoyé avec succès", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Non authentifié", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody MessageDto messageDto) {
        messageService.create(messageDto);
        MessageResponse message = new MessageResponse(MessageConstants.MESSAGE_SENT_SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
