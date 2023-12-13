package com.edstem.gamegearstore.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.edstem.gamegearstore.contract.request.LoginRequest;
import com.edstem.gamegearstore.contract.request.SignUpRequest;
import com.edstem.gamegearstore.contract.response.LoginResponse;
import com.edstem.gamegearstore.contract.response.SignUpResponse;
import com.edstem.gamegearstore.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserControllerTest {

    @InjectMocks private UserController userController;

    @Mock private UserService userService;

    @Before
    public void setUp() {}

    @Test
    public void testSignUp() {
        SignUpRequest signUpRequest = new SignUpRequest();

        SignUpResponse expectedResponse = new SignUpResponse();

        when(userService.signUp(any(SignUpRequest.class))).thenReturn(expectedResponse);

        SignUpResponse actualResponse = userController.signUp(signUpRequest);

        verify(userService).signUp(signUpRequest);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testLogin() {
        LoginRequest loginRequest = new LoginRequest();

        LoginResponse expectedResponse = new LoginResponse();

        when(userService.authenticate(any(LoginRequest.class))).thenReturn(expectedResponse);

        LoginResponse actualResponse = userController.login(loginRequest);

        verify(userService).authenticate(loginRequest);
        assertEquals(expectedResponse, actualResponse);
    }
}
