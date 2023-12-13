package com.edstem.gamegearstore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.edstem.gamegearstore.model.User;
import com.edstem.gamegearstore.security.JwtService;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

class JwtServiceTest {

    JwtService jwtService = new JwtService();

    @Test
    void testGenerateToken() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        String token = jwtService.generateToken(user);
        assertNotNull(token);
    }

    @Test
    void testExtractUserName() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        String token = jwtService.generateToken(user);
        String username = jwtService.extractUserName(token);

        assertEquals(user.getEmail(), username);
    }

    @Test
    void testIsTokenValid() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        String token = jwtService.generateToken(user);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(user.getEmail());

        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void testIsTokenExpired() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        String token = jwtService.generateToken(user);

        Method method = JwtService.class.getDeclaredMethod("isTokenExpired", String.class);
        method.setAccessible(true);

        Boolean isExpired = (Boolean) method.invoke(jwtService, token);

        assertFalse(isExpired);
    }
}
