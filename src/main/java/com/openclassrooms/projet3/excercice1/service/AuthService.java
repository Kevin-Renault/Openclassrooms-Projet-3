package com.openclassrooms.projet3.excercice1.service;

import com.openclassrooms.projet3.excercice1.constants.EntityConstants;
import com.openclassrooms.projet3.excercice1.dto.AuthResponse;
import com.openclassrooms.projet3.excercice1.dto.LoginRequest;
import com.openclassrooms.projet3.excercice1.dto.RegisterRequest;
import com.openclassrooms.projet3.excercice1.dto.UserDto;
import com.openclassrooms.projet3.excercice1.entity.User;
import com.openclassrooms.projet3.excercice1.exception.ResourceAlreadyExistsException;
import com.openclassrooms.projet3.excercice1.mapper.UserMapper;
import com.openclassrooms.projet3.excercice1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service gérant l'authentification et l'inscription des utilisateurs.
 * Gère la création de comptes, la connexion et la génération de tokens JWT.
 * 
 * @author Kévin Renault
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    /**
     * Enregistre un nouvel utilisateur dans le système.
     * Vérifie l'unicité de l'email, hash le mot de passe et génère un token JWT.
     * 
     * @param request Les données d'inscription (nom, email, mot de passe)
     * @return Une réponse contenant le token JWT et les données de l'utilisateur
     * @throws ResourceAlreadyExistsException Si l'email existe déjà
     */
    public AuthResponse register(RegisterRequest request) {
        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException(EntityConstants.ENTITY_USER, EntityConstants.FIELD_EMAIL,
                    request.getEmail());
        }

        // Créer l'utilisateur
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        // Générer le token JWT
        var userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);

        UserDto userDto = userMapper.toDto(savedUser);
        return new AuthResponse(jwtToken, userDto);
    }

    /**
     * Authentifie un utilisateur avec son email et mot de passe.
     * Génère un nouveau token JWT en cas de succès.
     * 
     * @param request Les identifiants de connexion (email, mot de passe)
     * @return Une réponse contenant le token JWT et les données de l'utilisateur
     * @throws RuntimeException Si l'authentification échoue ou si l'utilisateur
     *                          n'existe pas
     */
    public AuthResponse login(LoginRequest request) {
        // Authentifier l'utilisateur
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        // Charger l'utilisateur
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Générer le token JWT
        var userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);

        UserDto userDto = userMapper.toDto(user);
        return new AuthResponse(jwtToken, userDto);
    }

    /**
     * Récupère les informations de l'utilisateur actuellement connecté.
     * Utilise le token JWT pour identifier l'utilisateur.
     * 
     * @param authentication L'objet d'authentification contenant l'email de
     *                       l'utilisateur
     * @return Les données de l'utilisateur connecté
     * @throws RuntimeException Si l'utilisateur n'est pas trouvé
     */
    @Transactional(readOnly = true)
    public UserDto getCurrentUser(Authentication authentication) {
        // L'email est dans le principal (username) du token JWT
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return userMapper.toDto(user);
    }
}
