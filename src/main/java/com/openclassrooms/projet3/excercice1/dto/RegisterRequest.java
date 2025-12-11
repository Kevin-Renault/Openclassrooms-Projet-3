package com.openclassrooms.projet3.excercice1.dto;

import com.openclassrooms.projet3.excercice1.config.ValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Size(max = ValidationConstants.NAME_MAX_SIZE)
    private String name;

    @NotBlank
    @Email
    @Size(max = ValidationConstants.EMAIL_MAX_SIZE)
    private String email;

    @NotBlank
    @Size(min = ValidationConstants.PASSWORD_MIN_SIZE)
    private String password;
}
