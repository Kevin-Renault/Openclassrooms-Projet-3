package com.openclassrooms.projet3.excercice1.mapper;

import com.openclassrooms.projet3.excercice1.dto.RentalDto;
import com.openclassrooms.projet3.excercice1.entity.Rental;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {

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

    public void updateEntityFromDto(RentalDto dto, Rental rental) {
        if (dto == null || rental == null) {
            return;
        }

        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setPicture(dto.getPicture());
        rental.setDescription(dto.getDescription());
    }
}
