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
}
