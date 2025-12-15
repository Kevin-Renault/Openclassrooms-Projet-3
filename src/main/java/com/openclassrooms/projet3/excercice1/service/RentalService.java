package com.openclassrooms.projet3.excercice1.service;

import com.openclassrooms.projet3.excercice1.constants.EntityConstants;
import com.openclassrooms.projet3.excercice1.dto.RentalDto;
import com.openclassrooms.projet3.excercice1.entity.Rental;
import com.openclassrooms.projet3.excercice1.entity.User;
import com.openclassrooms.projet3.excercice1.exception.ResourceNotFoundException;
import com.openclassrooms.projet3.excercice1.mapper.RentalMapper;
import com.openclassrooms.projet3.excercice1.repository.RentalRepository;
import com.openclassrooms.projet3.excercice1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de gestion des locations immobilières.
 * Fournit les opérations CRUD pour les annonces de location.
 * 
 * @author Kévin Renault
 */
@Service
@RequiredArgsConstructor
@Transactional
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final RentalMapper rentalMapper;
    private final FileStorageService fileStorageService;

    /**
     * Crée une nouvelle annonce de location.
     * Gère également l'upload de l'image associée si présente.
     * 
     * @param rentalDto Les données de la location à créer
     * @param picture   L'image de la location (optionnel)
     * @return Les données de la location créée avec l'URL de l'image
     * @throws ResourceNotFoundException Si le propriétaire n'existe pas
     */
    public RentalDto create(RentalDto rentalDto, MultipartFile picture) {
        User owner = userRepository.findById(rentalDto.getOwnerId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(EntityConstants.ENTITY_USER, EntityConstants.FIELD_ID,
                                rentalDto.getOwnerId()));

        // Sauvegarder l'image si elle existe
        if (picture != null && !picture.isEmpty()) {
            String pictureUrl = fileStorageService.saveFile(picture);
            rentalDto.setPicture(pictureUrl);
        }

        Rental rental = rentalMapper.toEntity(rentalDto);
        rental.setOwner(owner);

        Rental savedRental = rentalRepository.save(rental);
        return rentalMapper.toDto(savedRental);
    }

    /**
     * Recherche une location par son identifiant.
     * 
     * @param id L'identifiant de la location
     * @return Les données de la location trouvée
     * @throws ResourceNotFoundException Si la location n'existe pas
     */
    @Transactional(readOnly = true)
    public RentalDto findById(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityConstants.ENTITY_RENTAL,
                        EntityConstants.FIELD_ID, id));
        return rentalMapper.toDto(rental);
    }

    /**
     * Récupère la liste de toutes les locations.
     * 
     * @return Liste de toutes les locations disponibles
     */
    @Transactional(readOnly = true)
    public List<RentalDto> findAll() {
        return rentalRepository.findAll().stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère toutes les locations appartenant à un propriétaire spécifique.
     * 
     * @param ownerId L'identifiant du propriétaire
     * @return Liste des locations du propriétaire
     */
    @Transactional(readOnly = true)
    public List<RentalDto> findByOwnerId(Long ownerId) {
        return rentalRepository.findByOwnerId(ownerId).stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Met à jour les informations d'une location.
     * Permet également de changer l'image si une nouvelle est fournie.
     * 
     * @param id        L'identifiant de la location à modifier
     * @param rentalDto Les nouvelles données de la location
     * @return Les données de la location mise à jour
     * @throws ResourceNotFoundException Si la location n'existe pas
     */
    public RentalDto update(Long id, RentalDto rentalDto) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityConstants.ENTITY_RENTAL,
                        EntityConstants.FIELD_ID, id));
        rentalMapper.updateEntityFromDto(rentalDto, rental);
        Rental updatedRental = rentalRepository.save(rental);
        return rentalMapper.toDto(updatedRental);
    }

    /**
     * Supprime une location du système.
     * 
     * @param id L'identifiant de la location à supprimer
     * @throws ResourceNotFoundException Si la location n'existe pas
     */
    public void delete(Long id) {
        if (!rentalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Location", "id", id);
        }
        rentalRepository.deleteById(id);
    }
}
