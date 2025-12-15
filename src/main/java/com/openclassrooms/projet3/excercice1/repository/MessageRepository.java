package com.openclassrooms.projet3.excercice1.repository;

import com.openclassrooms.projet3.excercice1.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour la gestion des messages.
 * Fournit les opérations CRUD standard et des méthodes de recherche
 * personnalisées.
 * 
 * @author Kévin Renault
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Recherche tous les messages associés à une location spécifique.
     * 
     * @param rentalId L'identifiant de la location
     * @return Liste des messages pour cette location
     */
    List<Message> findByRentalId(Long rentalId);

    /**
     * Recherche tous les messages envoyés par un utilisateur spécifique.
     * 
     * @param userId L'identifiant de l'utilisateur
     * @return Liste des messages de cet utilisateur
     */
    List<Message> findByUserId(Long userId);
}
