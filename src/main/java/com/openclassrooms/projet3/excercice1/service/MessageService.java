package com.openclassrooms.projet3.excercice1.service;

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

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final MessageMapper messageMapper;

    public MessageDto create(MessageDto messageDto) {
        User user = userRepository.findById(messageDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", messageDto.getUserId()));

        Rental rental = rentalRepository.findById(messageDto.getRentalId())
                .orElseThrow(() -> new ResourceNotFoundException("Location", "id", messageDto.getRentalId()));

        Message message = messageMapper.toEntity(messageDto);
        message.setUser(user);
        message.setRental(rental);

        Message savedMessage = messageRepository.save(message);
        return messageMapper.toDto(savedMessage);
    }

    @Transactional(readOnly = true)
    public MessageDto findById(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "id", id));
        return messageMapper.toDto(message);
    }

    @Transactional(readOnly = true)
    public List<MessageDto> findAll() {
        return messageRepository.findAll().stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MessageDto> findByRentalId(Long rentalId) {
        return messageRepository.findByRentalId(rentalId).stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MessageDto> findByUserId(Long userId) {
        return messageRepository.findByUserId(userId).stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    public MessageDto update(Long id, MessageDto messageDto) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "id", id));

        messageMapper.updateEntityFromDto(messageDto, message);
        Message updatedMessage = messageRepository.save(message);
        return messageMapper.toDto(updatedMessage);
    }

    public void delete(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Message", "id", id);
        }
        messageRepository.deleteById(id);
    }
}
