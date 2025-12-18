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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.openclassrooms.projet3.excercice1.constants.ApiConstants;
import com.openclassrooms.projet3.excercice1.service.JwtService;
import com.openclassrooms.projet3.excercice1.service.CustomUserDetailsService;
import com.openclassrooms.projet3.excercice1.service.MessageService;
import com.openclassrooms.projet3.excercice1.config.SecurityConfig;

@WebMvcTest(MessageController.class)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class MessageControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private MessageService messageService;

    @Test
    @DisplayName("POST /api/messages sans token -> 401")
    void createMessage_Unauthorized() throws Exception {
        mockMvc.perform(post(ApiConstants.API_MESSAGES_URL_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\":\"test\",\"rentalId\":1}"))
                .andExpect(status().isUnauthorized());
    }
}
