package com.openclassrooms.projet3.excercice1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO (Data Transfer Object) représentant une réponse contenant une liste de
 * locations.
 * Utilisé pour encapsuler les résultats lors de la récupération de plusieurs
 * locations.
 * 
 * @author Kévin Renault
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalResponse {

    /**
     * Liste des locations.
     */
    private List<RentalDto> rentals;
}
