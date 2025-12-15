package com.openclassrooms.projet3.excercice1.repository;

import com.openclassrooms.projet3.excercice1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository pour la gestion des utilisateurs.
 * Fournit les opérations CRUD standard et des méthodes de recherche
 * personnalisées.
 * 
 * @author Kévin Renault
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Recherche un utilisateur par son adresse email.
     * 
     * @param email L'adresse email à rechercher
     * @return Un Optional contenant l'utilisateur s'il existe, sinon vide
     */
    Optional<User> findByEmail(String email);

    /**
     * Vérifie si un utilisateur existe avec l'adresse email donnée.
     * 
     * @param email L'adresse email à vérifier
     * @return true si un utilisateur existe avec cet email, false sinon
     */
    boolean existsByEmail(String email);
}
