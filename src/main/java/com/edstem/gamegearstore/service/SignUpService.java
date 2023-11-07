package com.edstem.gamegearstore.service;

import com.edstem.gamegearstore.contract.request.SignUpRequest;
import com.edstem.gamegearstore.contract.response.SignUpResponse;
import com.edstem.gamegearstore.model.SignUp;
import com.edstem.gamegearstore.repository.SignUpRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final SignUpRepository signupRepository;
    private final ModelMapper modelMapper;
    public SignUpResponse register(SignUpRequest request){
        SignUp users =
                SignUp.builder()
                        .username(request.getUsername())
                        .password(request.getPassword())
                        .email(request.getEmail())
                        .build();
        users =signupRepository.save(users);
        return modelMapper.map(users, SignUpResponse.class);
    }

    public SignUpResponse getUsername(Long id) {
        SignUp signUp = signupRepository.findById(id).orElseThrow(() -> new RuntimeException("Feature not found"));
        return modelMapper.map(signUp, SignUpResponse.class);
    }
}
