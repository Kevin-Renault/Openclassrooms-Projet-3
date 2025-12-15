package com.openclassrooms.projet3.excercice1.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration pour la documentation OpenAPI/Swagger.
 * Définit le schéma de sécurité JWT pour permettre l'authentification dans
 * l'interface Swagger UI.
 * 
 * @author Kévin Renault
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configure OpenAPI avec le support de l'authentification JWT.
     * Ajoute un bouton "Authorize" dans Swagger UI pour saisir le token Bearer.
     * 
     * @return Configuration OpenAPI avec schéma de sécurité JWT
     */
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("ChâTop API")
                        .version("1.0")
                        .description("API de gestion de locations immobilières"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Entrez votre token JWT (sans 'Bearer ')")));
    }
}
