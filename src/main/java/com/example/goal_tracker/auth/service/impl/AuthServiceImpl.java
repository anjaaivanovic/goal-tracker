package com.example.goal_tracker.auth.service.impl;

import com.example.goal_tracker.auth.dto.LoginRequest;
import com.example.goal_tracker.auth.dto.LoginResponse;
import com.example.goal_tracker.auth.dto.RegisterRequest;
import com.example.goal_tracker.auth.exception.UserAlreadyExistsException;
import com.example.goal_tracker.auth.exception.UserNotFoundException;
import com.example.goal_tracker.auth.model.Role;
import com.example.goal_tracker.auth.model.User;
import com.example.goal_tracker.auth.repository.UserRepository;
import com.example.goal_tracker.auth.service.AuthService;
import com.example.goal_tracker.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws BadCredentialsException {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );

        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(UserNotFoundException::new);
        var token = jwtService.generateToken(user);

        return LoginResponse.builder().token(token).build();
    }

    @Override
    public LoginResponse register(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        var user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .role(Role.PERSONAL_USER)
                .build();

        userRepository.save(user);
        var token = jwtService.generateToken(user);

        return LoginResponse.builder().token(token).build();
    }
}