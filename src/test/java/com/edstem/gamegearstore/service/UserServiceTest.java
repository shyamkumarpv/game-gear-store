package com.edstem.gamegearstore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.edstem.gamegearstore.contract.request.LoginRequest;
import com.edstem.gamegearstore.contract.request.SignUpRequest;
import com.edstem.gamegearstore.contract.response.LoginResponse;
import com.edstem.gamegearstore.contract.response.SignUpResponse;
import com.edstem.gamegearstore.model.User;
import com.edstem.gamegearstore.repository.UserRepository;
import com.edstem.gamegearstore.security.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class UserServiceTest {

    @InjectMocks private UserService userService;

    @Mock private UserRepository userRepository;

    @Mock private PasswordEncoder passwordEncoder;

    @Mock private JwtService jwtService;

    @Mock private ModelMapper modelMapper;

    @Test
    void testSignUp() {
        SignUpRequest request = new SignUpRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");
        request.setName("Test User");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setHashedPassword("hashedPassword");
        user.setName(request.getName());

        when(userRepository.save(any(User.class))).thenReturn(user);

        SignUpResponse expectedResponse = new SignUpResponse();
        expectedResponse.setName(request.getName());
        expectedResponse.setEmail(request.getEmail());

        when(modelMapper.map(any(User.class), eq(SignUpResponse.class)))
                .thenReturn(expectedResponse);

        SignUpResponse response = userService.signUp(request);

        assertNotNull(response);
        assertEquals(expectedResponse.getName(), response.getName());
        assertEquals(expectedResponse.getEmail(), response.getEmail());
    }

    @Test
    void testAuthenticate() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setHashedPassword(passwordEncoder.encode(request.getPassword()));

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getHashedPassword()))
                .thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("token");

        LoginResponse response = userService.authenticate(request);

        assertNotNull(response);
        assertEquals("token", response.getToken());
    }
}
