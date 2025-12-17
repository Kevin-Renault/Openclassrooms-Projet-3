package com.openclassrooms.projet3.excercice1.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.openclassrooms.projet3.excercice1.constants.ApiConstants;
import com.openclassrooms.projet3.excercice1.service.JwtService;
import com.openclassrooms.projet3.excercice1.service.CustomUserDetailsService;
import com.openclassrooms.projet3.excercice1.service.UserService;
import com.openclassrooms.projet3.excercice1.service.AuthService;
import com.openclassrooms.projet3.excercice1.config.SecurityConfig;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class AuthControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthService authService;

    @Test
    @DisplayName("GET /api/auth/me sans token -> 401")
    void getCurrentUser_Unauthorized() throws Exception {
        mockMvc.perform(get(ApiConstants.AUTH_ME))
                .andExpect(status().isUnauthorized());
    }
}
