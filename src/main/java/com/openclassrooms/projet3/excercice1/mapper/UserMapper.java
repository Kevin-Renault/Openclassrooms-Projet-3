package com.openclassrooms.projet3.excercice1.mapper;

import com.openclassrooms.projet3.excercice1.dto.UserDto;
import com.openclassrooms.projet3.excercice1.dto.UserRegistrationDto;
import com.openclassrooms.projet3.excercice1.entity.User;
import org.springframework.stereotype.Component;

/**
 * Mapper pour la conversion entre l'entité User et ses DTOs.
 * Gère les transformations entre les objets de domaine et les objets de
 * transfert de données.
 * 
 * @author Kévin Renault
 */
@Component
public class UserMapper {

    /**
     * Convertit une entité User en UserDto.
     * 
     * @param user L'entité User à convertir
     * @return Le DTO correspondant, ou null si l'entité est null
     */
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }

    /**
     * Convertit un UserRegistrationDto en entité User.
     * Inclut le mot de passe du DTO d'inscription.
     * 
     * @param dto Le DTO d'inscription à convertir
     * @return L'entité correspondante, ou null si le DTO est null
     */
    public User toEntity(UserRegistrationDto dto) {
        if (dto == null) {
            return null;
        }
        User user = this.mapToEntity(dto);
        user.setPassword(dto.getPassword());
        return user;
    }

    /**
     * Convertit un UserDto en entité User.
     * N'inclut pas le mot de passe.
     * 
     * @param dto Le DTO à convertir
     * @return L'entité correspondante, ou null si le DTO est null
     */
    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        return this.mapToEntity(dto);
    }

    /**
     * Méthode privée de mapping des champs communs entre UserDto et User.
     * 
     * @param dto Le DTO source
     * @return L'entité User avec les champs mappés
     */
    private User mapToEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setCreatedAt(dto.getCreatedAt());
        user.setUpdatedAt(dto.getUpdatedAt());
        return user;
    }

    /**
     * Met à jour une entité User existante avec les données d'un UserDto.
     * Ne modifie que les champs autorisés (nom et email).
     * 
     * @param dto  Le DTO contenant les nouvelles données
     * @param user L'entité à mettre à jour
     */
    public void updateEntityFromDto(UserDto dto, User user) {
        if (dto == null || user == null) {
            return;
        }

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
    }
}
