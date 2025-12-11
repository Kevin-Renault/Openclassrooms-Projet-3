package com.openclassrooms.projet3.excercice1.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
        } else if (contentType != null && contentType.contains("multipart/form-data")) {
            log.info("📨 Request Body: [multipart/form-data - cannot be logged]");
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
