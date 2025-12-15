package com.openclassrooms.projet3.excercice1.mapper;

import com.openclassrooms.projet3.excercice1.dto.RentalDto;
import com.openclassrooms.projet3.excercice1.entity.Rental;
import org.springframework.stereotype.Component;

/**
 * Mapper pour la conversion entre l'entité Rental et son DTO.
 * Gère les transformations entre les objets de domaine et les objets de
 * transfert de données.
 * 
 * @author Kévin Renault
 */
@Component
public class RentalMapper {

    /**
     * Convertit une entité Rental en RentalDto.
     * Extrait l'ID du propriétaire depuis la relation.
     * 
     * @param rental L'entité Rental à convertir
     * @return Le DTO correspondant, ou null si l'entité est null
     */
    public RentalDto toDto(Rental rental) {
        if (rental == null) {
            return null;
        }

        RentalDto dto = new RentalDto();
        dto.setId(rental.getId());
        dto.setName(rental.getName());
        dto.setSurface(rental.getSurface());
        dto.setPrice(rental.getPrice());
        dto.setPicture(rental.getPicture());
        dto.setDescription(rental.getDescription());
        dto.setOwnerId(rental.getOwner() != null ? rental.getOwner().getId() : null);
        dto.setCreatedAt(rental.getCreatedAt());
        dto.setUpdatedAt(rental.getUpdatedAt());

        return dto;
    }

    /**
     * Convertit un RentalDto en entité Rental.
     * Note: Le propriétaire (owner) doit être défini séparément car seul l'ID est
     * présent dans le DTO.
     * 
     * @param dto Le DTO à convertir
     * @return L'entité correspondante, ou null si le DTO est null
     */
    public Rental toEntity(RentalDto dto) {
        if (dto == null) {
            return null;
        }

        Rental rental = new Rental();
        rental.setId(dto.getId());
        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setPicture(dto.getPicture());
        rental.setDescription(dto.getDescription());
        rental.setCreatedAt(dto.getCreatedAt());
        rental.setUpdatedAt(dto.getUpdatedAt());

        // Note: L'owner doit être défini séparément car on a seulement l'ID dans le DTO

        return rental;
    }

    /**
     * Met à jour une entité Rental existante avec les données d'un RentalDto.
     * Ne modifie pas le propriétaire, les dates ni l'image (picture).
     * L'image ne peut être définie qu'à la création.
     * 
     * @param dto    Le DTO contenant les nouvelles données
     * @param rental L'entité à mettre à jour
     */
    public void updateEntityFromDto(RentalDto dto, Rental rental) {
        if (dto == null || rental == null) {
            return;
        }

        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setDescription(dto.getDescription());
        // Note: picture n'est pas mis à jour - l'image ne peut être changée après
        // création
    }
}
