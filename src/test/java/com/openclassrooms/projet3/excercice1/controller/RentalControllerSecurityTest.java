package com.openclassrooms.projet3.excercice1.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.openclassrooms.projet3.excercice1.constants.ApiConstants;
import com.openclassrooms.projet3.excercice1.service.CustomUserDetailsService;
import com.openclassrooms.projet3.excercice1.service.JwtService;
import com.openclassrooms.projet3.excercice1.service.UserService;
import com.openclassrooms.projet3.excercice1.service.RentalService;
import com.openclassrooms.projet3.excercice1.service.AuthService;
import com.openclassrooms.projet3.excercice1.config.SecurityConfig;

@WebMvcTest(RentalController.class)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class RentalControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private UserService userService;

    @MockBean
    private RentalService rentalService;

    @MockBean
    private AuthService authService;

    @Test
    @DisplayName("POST /api/rentals sans token -> 401")
    void createRental_Unauthorized() throws Exception {
        mockMvc.perform(multipart(ApiConstants.RENTALS_BASE)
                .param("name", "Test")
                .param("surface", "10")
                .param("price", "100"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/rentals/{id} sans token -> 401")
    void getRentalById_Unauthorized() throws Exception {
        mockMvc.perform(get(ApiConstants.RENTALS_ID.replace("{id}", "1")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/rentals sans token -> 401")
    void getAllRentals_Unauthorized() throws Exception {
        mockMvc.perform(get(ApiConstants.RENTALS_BASE))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("PUT /api/rentals/{id} sans token -> 401")
    void updateRental_Unauthorized() throws Exception {
        mockMvc.perform(put(ApiConstants.RENTALS_ID.replace("{id}", "1"))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("name", "Test")
                .param("surface", "10")
                .param("price", "100"))
                .andExpect(status().isUnauthorized());
    }
}
