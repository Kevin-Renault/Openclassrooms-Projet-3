package com.openclassrooms.projet3.excercice1.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageService {

    @Value("${file.upload-dir:uploads/pictures}")
    private String uploadDir;

    @Value("${app.base-url:http://localhost:3001}")
    private String baseUrl;

    private Path fileStorageLocation;

    @PostConstruct
    public void init() {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
            log.info("Dossier d'upload créé: {}", this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Impossible de créer le dossier d'upload", ex);
        }
    }

    /**
     * Sauvegarde un fichier et retourne l'URL relative
     */
    public String saveFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            // Nettoyer et valider le nom de fichier
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());

            if (originalFilename.contains("..")) {
                throw new IllegalArgumentException("Nom de fichier invalide: " + originalFilename);
            }

            // Générer un nom unique
            String fileExtension = getFileExtension(originalFilename);
            String uniqueFilename = UUID.randomUUID().toString() + "." + fileExtension;

            // Sauvegarder le fichier
            Path targetLocation = this.fileStorageLocation.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            log.info("Fichier sauvegardé: {}", uniqueFilename);

            // Retourner l'URL absolue
            return baseUrl + "/uploads/pictures/" + uniqueFilename;

        } catch (IOException ex) {
            throw new RuntimeException("Erreur lors de la sauvegarde du fichier: " + file.getOriginalFilename(), ex);
        }
    }

    /**
     * Supprime un fichier à partir de son URL
     */
    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return;
        }

        try {
            // Extraire le nom de fichier de l'URL
            String filename = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Files.deleteIfExists(filePath);
            log.info("Fichier supprimé: {}", filename);
        } catch (IOException ex) {
            log.error("Erreur lors de la suppression du fichier: {}", fileUrl, ex);
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
