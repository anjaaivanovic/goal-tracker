package com.example.goal_tracker.auth.exception;

import jakarta.persistence.EntityExistsException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserAlreadyExistsException extends EntityExistsException {
    private final HttpStatus status = HttpStatus.BAD_REQUEST;

    public UserAlreadyExistsException() {
        super("User already exists.");
    }
}