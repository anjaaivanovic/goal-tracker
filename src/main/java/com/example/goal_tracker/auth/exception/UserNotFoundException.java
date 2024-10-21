package com.example.goal_tracker.auth.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserNotFoundException extends EntityNotFoundException {
    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public UserNotFoundException() {
        super("User not found.");
    }
}