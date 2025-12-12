package com.openclassrooms.projet3.excercice1.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Wrapper pour capturer la requête et la réponse
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request, 1024);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        // Logger la requête avec ses en-têtes
        String contentType = request.getContentType();
        log.info("📥 Requête: {} {} - Content-Type: {}", request.getMethod(), request.getRequestURI(), contentType);

        // Logger les paramètres de la requête si présents
        if (request.getParameterMap() != null && !request.getParameterMap().isEmpty()) {
            StringBuilder params = new StringBuilder();
            request.getParameterMap().forEach((key, values) -> {
                params.append(key).append("=").append(String.join(",", values)).append(" ");
            });
            log.info("📨 Request Parameters: {}", params.toString().trim());
        }

        // Pour multipart/form-data, afficher les champs non-binaires
        if (contentType != null && contentType.contains("multipart/form-data")) {
            try {
                StringBuilder multipartData = new StringBuilder();
                for (Part part : request.getParts()) {
                    String partName = part.getName();
                    String fileName = part.getSubmittedFileName();

                    if (fileName != null) {
                        // C'est un fichier
                        multipartData.append(String.format("  %s: [FILE: %s, size: %d bytes, type: %s]\n",
                                partName, fileName, part.getSize(), part.getContentType()));
                    } else {
                        // C'est un champ texte
                        try (BufferedReader reader = new BufferedReader(
                                new InputStreamReader(part.getInputStream(), StandardCharsets.UTF_8))) {
                            String value = reader.lines().collect(Collectors.joining("\n"));
                            multipartData.append(String.format("  %s: %s\n", partName, value));
                        }
                    }
                }
                log.info("📨 Multipart Form Data:\n{}", multipartData.toString());
            } catch (Exception e) {
                log.warn("⚠️ Impossible de lire les parts multipart: {}", e.getMessage());
            }
        }

        // Exécuter la chaîne de filtres
        filterChain.doFilter(requestWrapper, responseWrapper);

        // Logger le body de la requête (seulement pour JSON)
        if (contentType != null && contentType.contains("application/json")) {
            byte[] requestArray = requestWrapper.getContentAsByteArray();
            if (requestArray.length > 0) {
                String requestBody = new String(requestArray, StandardCharsets.UTF_8);
                try {
                    Object json = objectMapper.readValue(requestBody, Object.class);
                    String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
                    log.info("📨 Request Body:\n{}", prettyJson);
                } catch (Exception e) {
                    log.info("📨 Request Body: {}", requestBody);
                }
            }
        }

        // Logger la réponse
        byte[] responseArray = responseWrapper.getContentAsByteArray();

        log.info("📤 Réponse: {} {} - Status: {}",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus());

        if (responseArray.length > 0) {
            String responseBody = new String(responseArray, StandardCharsets.UTF_8);
            try {
                // Formater le JSON pour qu'il soit lisible
                Object json = objectMapper.readValue(responseBody, Object.class);
                String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
                log.info("📋 Response Body:\n{}", prettyJson);
            } catch (Exception e) {
                // Si ce n'est pas du JSON, afficher tel quel
                log.info("📋 Response Body: {}", responseBody);
            }
        }

        // Important: copier le contenu dans la vraie réponse
        responseWrapper.copyBodyToResponse();
    }
}
