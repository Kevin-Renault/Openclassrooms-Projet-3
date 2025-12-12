package com.openclassrooms.projet3.excercice1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalResponse {
    private List<RentalDto> rentals;
}
