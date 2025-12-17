package com.openclassrooms.projet3.excercice1.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.projet3.excercice1.exception.FileStorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Service de gestion du stockage des fichiers (images de locations).
 * Gère l'upload, la suppression et le nommage des fichiers.
 * 
 * @author Kévin Renault
 */
@Slf4j
@Service
public class FileStorageService {

    @Value("${file.upload-dir:uploads/pictures}")
    private String uploadDir;

    @Value("${app.base-url:http://localhost:3001}")
    private String baseUrl;

    private Path fileStorageLocation;

    /**
     * Initialise le répertoire de stockage au démarrage de l'application.
     * Crée le répertoire s'il n'existe pas.
     * 
     * @throws RuntimeException Si le répertoire ne peut pas être créé
     */
    @PostConstruct
    public void init() {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
            log.info("Dossier d'upload créé: {}", this.fileStorageLocation);
        } catch (IOException ex) {
            throw new FileStorageException(
                    String.format("Impossible de créer le dossier d'upload : %s", ex.getMessage()));
        }
    }

    /**
     * Sauvegarde un fichier et retourne l'URL complète.
     * Génère un nom unique avec UUID pour éviter les collisions.
     * 
     * @param file Le fichier à sauvegarder
     * @return L'URL absolue du fichier sauvegardé, ou null si le fichier est vide
     * @throws IllegalArgumentException Si le nom de fichier contient des caractères
     *                                  invalides
     * @throws RuntimeException         Si une erreur d'I/O survient lors de la
     *                                  sauvegarde
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
            if (!targetLocation.startsWith(this.fileStorageLocation)) {
                throw new FileStorageException("Tentative d'accès non autorisé détectée");
            }
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            log.info("Fichier sauvegardé: {}", uniqueFilename);

            // Retourner l'URL absolue
            return baseUrl + "/uploads/pictures/" + uniqueFilename;

        } catch (IOException ex) {
            throw new FileStorageException(
                    String.format("Erreur lors de la sauvegarde du fichier %s : %s", file.getOriginalFilename(),
                            ex.getMessage()));
        }
    }

    /**
     * Supprime un fichier à partir de son URL.
     * Extrait le nom de fichier de l'URL et le supprime du système de fichiers.
     * 
     * @param fileUrl L'URL complète du fichier à supprimer
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

    /**
     * Extrait l'extension d'un nom de fichier.
     * 
     * @param filename Le nom complet du fichier
     * @return L'extension du fichier (sans le point), ou chaîne vide si aucune
     *         extension
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
