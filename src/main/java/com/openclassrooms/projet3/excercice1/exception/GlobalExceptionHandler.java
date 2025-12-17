package com.openclassrooms.projet3.excercice1.exception;

import com.openclassrooms.projet3.excercice1.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

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
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    /**
     * Gère les violations de contraintes sur les paramètres
     * (@RequestParam, @PathVariable).
     * Utilisé quand @Validated est activé sur la classe du contrôleur.
     * 
     * @param ex L'exception de violation de contrainte
     * @return Une réponse HTTP 400 avec les détails des erreurs
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        String errorMessage = ex.getAllValidationResults()
                .stream()
                .flatMap(result -> result.getResolvableErrors().stream())
                .map(error -> {
                    String paramName = error.getCodes() != null && error.getCodes().length > 0
                            ? error.getCodes()[0].substring(error.getCodes()[0].lastIndexOf('.') + 1)
                            : "paramètre";
                    return paramName + ": " + error.getDefaultMessage();
                })
                .collect(Collectors.joining(", "));
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
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
        return buildErrorResponse(status, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus statusCode,
            String errorMessage) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                statusCode.value(),
                errorMessage);

        return ResponseEntity
                .status(statusCode)
                .body(errorResponse);
    }
}
