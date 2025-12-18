package com.openclassrooms.projet3.excercice1.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.projet3.excercice1.service.JwtService;
import com.openclassrooms.projet3.excercice1.service.CustomUserDetailsService;
import com.openclassrooms.projet3.excercice1.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.openclassrooms.projet3.excercice1.constants.ApiConstants;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("GET " + ApiConstants.API_USER_URL_ID + " sans token -> 401")
    void getUserById_Unauthorized() throws Exception {
        mockMvc.perform(get(ApiConstants.API_USER_URL_ID.replace("{id}", "1")))
                .andExpect(status().isUnauthorized());
    }
}
