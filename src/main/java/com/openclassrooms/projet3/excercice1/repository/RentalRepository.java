package com.openclassrooms.projet3.excercice1.repository;

import com.openclassrooms.projet3.excercice1.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour la gestion des locations.
 * Fournit les opérations CRUD standard et des méthodes de recherche
 * personnalisées.
 * 
 * @author Kévin Renault
 */
@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    /**
     * Recherche toutes les locations appartenant à un propriétaire spécifique.
     * 
     * @param ownerId L'identifiant du propriétaire
     * @return Liste des locations du propriétaire
     */
    List<Rental> findByOwnerId(Long ownerId);
}
