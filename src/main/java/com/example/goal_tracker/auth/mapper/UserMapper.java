package com.example.goal_tracker.auth.mapper;

import com.example.goal_tracker.auth.dto.RegisterRequest;
import com.example.goal_tracker.auth.model.User;
import org.mapstruct.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegisterRequest registerRequest);

    default User toUserWithEncodedPassword(RegisterRequest registerRequest, PasswordEncoder passwordEncoder) {
        User user = toUser(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return user;
    }
}