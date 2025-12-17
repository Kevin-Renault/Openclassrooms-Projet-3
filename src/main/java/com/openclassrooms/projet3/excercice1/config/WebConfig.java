package com.openclassrooms.projet3.excercice1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuration Web de l'application.
 * Configure les gestionnaires de ressources statiques, notamment pour les
 * fichiers uploadés.
 * 
 * @author Kévin Renault
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir:uploads/pictures}")
    private String uploadDir;

    /**
     * Configure les gestionnaires de ressources statiques.
     * Permet de servir les fichiers uploadés via HTTP.
     * 
     * @param registry Le registre des gestionnaires de ressources
     */
    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // Servir les fichiers depuis le dossier uploads
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        String uploadPathString = uploadPath.toUri().toString();

        registry.addResourceHandler("/uploads/pictures/**")
                .addResourceLocations(uploadPathString);
    }
}
