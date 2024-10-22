package com.example.goal_tracker.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserEngagedException extends RuntimeException {
    private final HttpStatus status = HttpStatus.BAD_REQUEST;

    public UserEngagedException() {
        super("Could not delete user. User is engaged on multiple goals.");
    }
}