package com.edstem.gamegearstore.controller;

import com.edstem.gamegearstore.contract.request.SignUpRequest;
import com.edstem.gamegearstore.contract.response.SignUpResponse;
import com.edstem.gamegearstore.service.SignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpService signUpService;

    @PostMapping("/signup")
    public SignUpResponse signup(@Valid @RequestBody SignUpRequest request)
    {
        return signUpService.register(request);
    }
    @GetMapping("/username/{id}")
    public SignUpResponse getUsername(@PathVariable Long id) {
        return signUpService.getUsername(id);
    }
}