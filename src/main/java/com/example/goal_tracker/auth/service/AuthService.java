package com.example.goal_tracker.auth.service;

import com.example.goal_tracker.auth.dto.LoginRequest;
import com.example.goal_tracker.auth.dto.LoginResponse;
import com.example.goal_tracker.auth.dto.RegisterRequest;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    LoginResponse register(RegisterRequest registerRequest);
}