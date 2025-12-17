package com.openclassrooms.projet3.excercice1.exception;

import com.openclassrooms.projet3.excercice1.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Gestionnaire global des exceptions de l'application.
 * Intercepte et formate les exceptions pour retourner des réponses d'erreur
 * standardisées.
 * 
 * @author Kévin Renault
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gère les exceptions de validation des requêtes.
     * Concatène toutes les erreurs de validation en un seul message.
     * 
     * @param ex L'exception de validation levée
     * @return Une réponse HTTP 400 avec les détails des erreurs
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                errorMessage);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    /**
     * Gère toutes les autres exceptions non interceptées spécifiquement.
     * Détermine le code HTTP approprié selon le type d'exception.
     * 
     * @param ex L'exception levée
     * @return Une réponse HTTP avec le code de statut approprié
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        HttpStatus status;
        if (ex instanceof ApiException exception) {
            status = exception.getHttpStatus();
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                ex.getMessage());

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }
}
