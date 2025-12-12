package com.openclassrooms.projet3.excercice1.service;

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

@Service
@RequiredArgsConstructor
@Transactional
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final RentalMapper rentalMapper;
    private final FileStorageService fileStorageService;

    public RentalDto create(RentalDto rentalDto, MultipartFile picture) {
        User owner = userRepository.findById(rentalDto.getOwnerId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Utilisateur", "id", rentalDto.getOwnerId()));

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

    @Transactional(readOnly = true)
    public RentalDto findById(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location", "id", id));
        return rentalMapper.toDto(rental);
    }

    @Transactional(readOnly = true)
    public List<RentalDto> findAll() {
        return rentalRepository.findAll().stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RentalDto> findByOwnerId(Long ownerId) {
        return rentalRepository.findByOwnerId(ownerId).stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }

    public RentalDto update(Long id, RentalDto rentalDto) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location", "id", id));

        // Vérifier si le propriétaire change
        if (rentalDto.getOwnerId() != null &&
                !rental.getOwner().getId().equals(rentalDto.getOwnerId())) {
            User newOwner = userRepository.findById(rentalDto.getOwnerId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Utilisateur", "id", rentalDto.getOwnerId()));
            rental.setOwner(newOwner);
        }

        rentalMapper.updateEntityFromDto(rentalDto, rental);
        Rental updatedRental = rentalRepository.save(rental);
        return rentalMapper.toDto(updatedRental);
    }

    public void delete(Long id) {
        if (!rentalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Location", "id", id);
        }
        rentalRepository.deleteById(id);
    }
}
