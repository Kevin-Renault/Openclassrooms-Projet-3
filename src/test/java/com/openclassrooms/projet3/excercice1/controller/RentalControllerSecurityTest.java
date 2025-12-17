package com.openclassrooms.projet3.excercice1.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RentalControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST /api/rentals sans token -> 401")
    void createRental_Unauthorized() throws Exception {
        mockMvc.perform(multipart("/api/rentals")
                .param("name", "Test")
                .param("surface", "10")
                .param("price", "100"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/rentals/{id} sans token -> 401")
    void getRentalById_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/rentals/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/rentals sans token -> 401")
    void getAllRentals_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/rentals"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("PUT /api/rentals/{id} sans token -> 401")
    void updateRental_Unauthorized() throws Exception {
        mockMvc.perform(put("/api/rentals/1")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("name", "Test")
                .param("surface", "10")
                .param("price", "100"))
                .andExpect(status().isUnauthorized());
    }
}
