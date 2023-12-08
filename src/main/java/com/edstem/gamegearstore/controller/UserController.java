package com.edstem.gamegearstore.controller;

import com.edstem.gamegearstore.contract.request.LoginRequest;
import com.edstem.gamegearstore.contract.request.SignUpRequest;
import com.edstem.gamegearstore.contract.response.LoginResponse;
import com.edstem.gamegearstore.contract.response.SignUpResponse;
import com.edstem.gamegearstore.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public SignUpResponse signUp(@Valid @RequestBody SignUpRequest request) {
        return userService.signUp(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.authenticate(request);
    }
}
