package com.edstem.gamegearstore.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edstem.gamegearstore.contract.request.LoginRequest;
import com.edstem.gamegearstore.contract.request.SignUpRequest;
import com.edstem.gamegearstore.contract.response.SignUpResponse;
import com.edstem.gamegearstore.repository.UserRepository;
import com.edstem.gamegearstore.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean private UserRepository userRepository;

    @Autowired private UserController userController;
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private UserService userService;

    @Test
    public void testSignUp() throws Exception {
        SignUpRequest request = new SignUpRequest();
        request.setName("testUser");
        request.setPassword("testPassword");
        request.setEmail("testEmail@test.com");

        SignUpResponse response = new SignUpResponse();
        response.setName("testUser");
        response.setEmail("testEmail@test.com");
        response.setHashedPassword("testPassword");

        when(userService.signUp(any(SignUpRequest.class))).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/user/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogin() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("testEmail@test.com");
        request.setPassword("testPassword");
        Long expectedUserId = 1L;

        when(userService.login(any(LoginRequest.class))).thenReturn(expectedUserId);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedUserId.toString()));
    }
}
