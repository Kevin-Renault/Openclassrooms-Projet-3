package com.openclassrooms.projet3.excercice1.service;

import com.openclassrooms.projet3.excercice1.entity.User;
import com.openclassrooms.projet3.excercice1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service personnalisé pour charger les détails d'un utilisateur depuis la base
 * de données.
 * Implémente UserDetailsService de Spring Security pour l'authentification.
 * 
 * @author Kévin Renault
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Charge les détails d'un utilisateur par son email (utilisé comme username).
     * Utilisé par Spring Security lors de l'authentification.
     * 
     * @param email L'adresse email de l'utilisateur
     * @return Les détails de l'utilisateur au format UserDetails
     * @throws UsernameNotFoundException Si aucun utilisateur n'est trouvé avec cet
     *                                   email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>() // Pas de rôles pour le moment
        );
    }
}
