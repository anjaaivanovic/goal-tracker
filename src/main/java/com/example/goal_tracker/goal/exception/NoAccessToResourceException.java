package com.example.goal_tracker.goal.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NoAccessToResourceException extends RuntimeException {

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    public NoAccessToResourceException() {
        super("You don't have access to this resource.");
    }
}