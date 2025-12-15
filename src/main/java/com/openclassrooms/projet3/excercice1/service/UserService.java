package com.openclassrooms.projet3.excercice1.service;

import com.openclassrooms.projet3.excercice1.constants.EntityConstants;
import com.openclassrooms.projet3.excercice1.dto.UserDto;
import com.openclassrooms.projet3.excercice1.entity.User;
import com.openclassrooms.projet3.excercice1.exception.ResourceNotFoundException;
import com.openclassrooms.projet3.excercice1.mapper.UserMapper;
import com.openclassrooms.projet3.excercice1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service de gestion des utilisateurs.
 * Fournit les opérations CRUD et de gestion des comptes utilisateurs.
 * 
 * @author Kévin Renault
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Recherche un utilisateur par son identifiant.
     * 
     * @param id L'identifiant de l'utilisateur
     * @return Les données de l'utilisateur trouvé
     * @throws ResourceNotFoundException Si l'utilisateur n'existe pas
     */
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(EntityConstants.ENTITY_USER, EntityConstants.FIELD_ID, id));
        return userMapper.toDto(user);
    }

    /**
     * Recherche un utilisateur par son adresse email.
     * 
     * @param email L'adresse email de l'utilisateur
     * @return Les données de l'utilisateur trouvé
     * @throws ResourceNotFoundException Si l'utilisateur n'existe pas
     */
    @Transactional(readOnly = true)
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(EntityConstants.ENTITY_USER,
                        EntityConstants.FIELD_EMAIL, email));
        return userMapper.toDto(user);
    }
}
