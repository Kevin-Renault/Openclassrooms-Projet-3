package com.openclassrooms.projet3.excercice1.mapper;

import com.openclassrooms.projet3.excercice1.dto.MessageDto;
import com.openclassrooms.projet3.excercice1.entity.Message;
import org.springframework.stereotype.Component;

/**
 * Mapper pour la conversion entre l'entité Message et son DTO.
 * Gère les transformations entre les objets de domaine et les objets de
 * transfert de données.
 * 
 * @author Kévin Renault
 */
@Component
public class MessageMapper {

    /**
     * Convertit une entité Message en MessageDto.
     * Extrait les IDs de la location et de l'utilisateur depuis les relations.
     * 
     * @param message L'entité Message à convertir
     * @return Le DTO correspondant, ou null si l'entité est null
     */
    public MessageDto toDto(Message message) {
        if (message == null) {
            return null;
        }

        MessageDto dto = new MessageDto();
        dto.setId(message.getId());
        dto.setRentalId(message.getRental() != null ? message.getRental().getId() : null);
        dto.setUserId(message.getUser() != null ? message.getUser().getId() : null);
        dto.setMessageContent(message.getMessageContent());
        dto.setCreatedAt(message.getCreatedAt());
        dto.setUpdatedAt(message.getUpdatedAt());

        return dto;
    }

    /**
     * Convertit un MessageDto en entité Message.
     * Note: Les relations rental et user doivent être définies séparément car seuls
     * les IDs sont présents dans le DTO.
     * 
     * @param dto Le DTO à convertir
     * @return L'entité correspondante, ou null si le DTO est null
     */
    public Message toEntity(MessageDto dto) {
        if (dto == null) {
            return null;
        }

        Message message = new Message();
        message.setId(dto.getId());
        message.setMessageContent(dto.getMessageContent());
        message.setCreatedAt(dto.getCreatedAt());
        message.setUpdatedAt(dto.getUpdatedAt());

        // Note: rental et user doivent être définis séparément car on a seulement les
        // IDs dans le DTO

        return message;
    }

    /**
     * Met à jour une entité Message existante avec les données d'un MessageDto.
     * Ne modifie que le contenu du message.
     * 
     * @param dto     Le DTO contenant les nouvelles données
     * @param message L'entité à mettre à jour
     */
    public void updateEntityFromDto(MessageDto dto, Message message) {
        if (dto == null || message == null) {
            return;
        }

        message.setMessageContent(dto.getMessageContent());
    }
}
