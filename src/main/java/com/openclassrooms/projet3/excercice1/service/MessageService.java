package com.openclassrooms.projet3.excercice1.service;

import com.openclassrooms.projet3.excercice1.constants.EntityConstants;
import com.openclassrooms.projet3.excercice1.dto.MessageDto;
import com.openclassrooms.projet3.excercice1.entity.Message;
import com.openclassrooms.projet3.excercice1.entity.Rental;
import com.openclassrooms.projet3.excercice1.entity.User;
import com.openclassrooms.projet3.excercice1.exception.ResourceNotFoundException;
import com.openclassrooms.projet3.excercice1.mapper.MessageMapper;
import com.openclassrooms.projet3.excercice1.repository.MessageRepository;
import com.openclassrooms.projet3.excercice1.repository.RentalRepository;
import com.openclassrooms.projet3.excercice1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de gestion des messages.
 * Permet aux utilisateurs d'envoyer des messages concernant les locations.
 * 
 * @author Kévin Renault
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final MessageMapper messageMapper;

    /**
     * Crée un nouveau message.
     * Vérifie que l'utilisateur et la location existent avant la création.
     * 
     * @param messageDto Les données du message à créer
     * @return Les données du message créé
     * @throws ResourceNotFoundException Si l'utilisateur ou la location n'existe
     *                                   pas
     */
    public MessageDto create(MessageDto messageDto) {
        User user = userRepository.findById(messageDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(EntityConstants.ENTITY_USER, EntityConstants.FIELD_ID,
                        messageDto.getUserId()));

        Rental rental = rentalRepository.findById(messageDto.getRentalId())
                .orElseThrow(() -> new ResourceNotFoundException(EntityConstants.ENTITY_RENTAL,
                        EntityConstants.FIELD_ID, messageDto.getRentalId()));

        Message message = messageMapper.toEntity(messageDto);
        message.setUser(user);
        message.setRental(rental);

        Message savedMessage = messageRepository.save(message);
        return messageMapper.toDto(savedMessage);
    }

    /**
     * Recherche un message par son identifiant.
     * 
     * @param id L'identifiant du message
     * @return Les données du message trouvé
     * @throws ResourceNotFoundException Si le message n'existe pas
     */
    @Transactional(readOnly = true)
    public MessageDto findById(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityConstants.ENTITY_MESSAGE,
                        EntityConstants.FIELD_ID, id));
        return messageMapper.toDto(message);
    }

    /**
     * Récupère la liste de tous les messages.
     * 
     * @return Liste de tous les messages
     */
    @Transactional(readOnly = true)
    public List<MessageDto> findAll() {
        return messageRepository.findAll().stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère tous les messages associés à une location spécifique.
     * 
     * @param rentalId L'identifiant de la location
     * @return Liste des messages pour cette location
     */
    @Transactional(readOnly = true)
    public List<MessageDto> findByRentalId(Long rentalId) {
        return messageRepository.findByRentalId(rentalId).stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère tous les messages envoyés par un utilisateur spécifique.
     * 
     * @param userId L'identifiant de l'utilisateur
     * @return Liste des messages de cet utilisateur
     */
    @Transactional(readOnly = true)
    public List<MessageDto> findByUserId(Long userId) {
        return messageRepository.findByUserId(userId).stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Met à jour le contenu d'un message.
     * 
     * @param id         L'identifiant du message à modifier
     * @param messageDto Les nouvelles données du message
     * @return Les données du message mis à jour
     * @throws ResourceNotFoundException Si le message n'existe pas
     */
    public MessageDto update(Long id, MessageDto messageDto) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "id", id));

        messageMapper.updateEntityFromDto(messageDto, message);
        Message updatedMessage = messageRepository.save(message);
        return messageMapper.toDto(updatedMessage);
    }

    /**
     * Supprime un message du système.
     * 
     * @param id L'identifiant du message à supprimer
     * @throws ResourceNotFoundException Si le message n'existe pas
     */
    public void delete(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Message", "id", id);
        }
        messageRepository.deleteById(id);
    }
}
