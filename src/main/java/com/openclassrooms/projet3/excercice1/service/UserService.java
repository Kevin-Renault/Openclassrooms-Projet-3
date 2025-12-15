package com.openclassrooms.projet3.excercice1.service;

import com.openclassrooms.projet3.excercice1.constants.EntityConstants;
import com.openclassrooms.projet3.excercice1.dto.UserDto;
import com.openclassrooms.projet3.excercice1.dto.UserRegistrationDto;
import com.openclassrooms.projet3.excercice1.entity.User;
import com.openclassrooms.projet3.excercice1.exception.ResourceAlreadyExistsException;
import com.openclassrooms.projet3.excercice1.exception.ResourceNotFoundException;
import com.openclassrooms.projet3.excercice1.mapper.UserMapper;
import com.openclassrooms.projet3.excercice1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    private final PasswordEncoder passwordEncoder;

    /**
     * Crée un nouvel utilisateur dans le système.
     * Le mot de passe est automatiquement hashé avant la sauvegarde.
     * 
     * @param userDto Les données de l'utilisateur à créer
     * @return Les données de l'utilisateur créé
     * @throws ResourceAlreadyExistsException Si un utilisateur existe déjà avec cet
     *                                        email
     */
    public UserDto create(UserRegistrationDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ResourceAlreadyExistsException(EntityConstants.ENTITY_USER, EntityConstants.FIELD_EMAIL,
                    userDto.getEmail());
        }

        User user = userMapper.toEntity(userDto);
        // Hasher le mot de passe avant de sauvegarder
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

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

    /**
     * Récupère la liste de tous les utilisateurs.
     * 
     * @return Liste de tous les utilisateurs
     */
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Met à jour les informations d'un utilisateur.
     * Note: Le mot de passe ne peut pas être modifié via cette méthode.
     * 
     * @param id      L'identifiant de l'utilisateur à modifier
     * @param userDto Les nouvelles données de l'utilisateur
     * @return Les données de l'utilisateur mis à jour
     * @throws ResourceNotFoundException      Si l'utilisateur n'existe pas
     * @throws ResourceAlreadyExistsException Si le nouvel email est déjà utilisé
     */
    public UserDto update(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(EntityConstants.ENTITY_USER, EntityConstants.FIELD_ID, id));

        // Vérifier si l'email est déjà utilisé par un autre utilisateur
        if (!user.getEmail().equals(userDto.getEmail()) &&
                userRepository.existsByEmail(userDto.getEmail())) {
            throw new ResourceAlreadyExistsException(EntityConstants.ENTITY_USER, EntityConstants.FIELD_EMAIL,
                    userDto.getEmail());
        }

        userMapper.updateEntityFromDto(userDto, user);
        // Note: Le mot de passe n'est pas modifié via update pour des raisons de
        // sécurité
        // Utilisez une méthode dédiée pour changer le mot de passe
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    /**
     * Met à jour le mot de passe d'un utilisateur.
     * Le nouveau mot de passe est automatiquement hashé.
     * 
     * @param id          L'identifiant de l'utilisateur
     * @param newPassword Le nouveau mot de passe en clair
     * @throws ResourceNotFoundException Si l'utilisateur n'existe pas
     */
    public void updatePassword(Long id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(EntityConstants.ENTITY_USER, EntityConstants.FIELD_ID, id));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * Supprime un utilisateur du système.
     * 
     * @param id L'identifiant de l'utilisateur à supprimer
     * @throws ResourceNotFoundException Si l'utilisateur n'existe pas
     */
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(EntityConstants.ENTITY_USER, EntityConstants.FIELD_ID, id);
        }
        userRepository.deleteById(id);
    }
}
