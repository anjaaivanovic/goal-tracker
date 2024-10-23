package com.example.goal_tracker.auth.dto;

import com.example.goal_tracker.auth.model.Role;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Role role;
}