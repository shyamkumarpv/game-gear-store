package com.edstem.gamegearstore.service;

import com.edstem.gamegearstore.contract.request.LoginRequest;
import com.edstem.gamegearstore.contract.request.SignUpRequest;
import com.edstem.gamegearstore.contract.response.LoginResponse;
import com.edstem.gamegearstore.contract.response.SignUpResponse;
import com.edstem.gamegearstore.model.User;
import com.edstem.gamegearstore.repository.UserRepository;
import com.edstem.gamegearstore.security.JwtService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public SignUpResponse signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EntityExistsException("Invalid Signup");
        }
        User user =
                User.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .hashedPassword(passwordEncoder.encode(request.getPassword()))
                        //                        .carts(request.getCart())
                        .build();
        user = userRepository.save(user);
        return modelMapper.map(user, SignUpResponse.class);
    }

//    public Long login(LoginRequest request) {
//        String email = request.getEmail();
//        String password = request.getPassword();
//        if (!userRepository.existsByEmail(email)) {
//            throw new EntityNotFoundException("Invalid login");
//        }
//        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
//        if (passwordEncoder.matches(password, user.getHashedPassword())) {
//            return user.getId();
//        }
//        throw new EntityNotFoundException("user not found");
//public LoginResponse authenticate(LoginRequest request) {
//    authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//    User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
//    String jwtToken = jwtService.generateToken(user);
//    return LoginResponse.builder().token(jwtToken).build();
//}

    public LoginResponse authenticate(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getHashedPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String jwtToken = jwtService.generateToken(user);

        return LoginResponse.builder().token(jwtToken).build();
    }

    }

