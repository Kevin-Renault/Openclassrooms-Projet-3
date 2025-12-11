package com.openclassrooms.projet3.excercice1.dto;

import com.openclassrooms.projet3.excercice1.config.ValidationConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {

    private Long id;

    @NotBlank
    @Size(max = ValidationConstants.NAME_MAX_SIZE)
    private String name;

    @NotNull
    @Positive
    private Integer surface;

    @NotNull
    @Positive
    private Integer price;

    @Size(max = ValidationConstants.PICTURE_MAX_SIZE)
    private String picture;

    @Size(max = ValidationConstants.DESCRIPTION_MAX_SIZE)
    private String description;

    @NotNull
    @Positive
    private Long ownerId;

    @PastOrPresent
    private LocalDateTime createdAt;

    @PastOrPresent
    private LocalDateTime updatedAt;
}
