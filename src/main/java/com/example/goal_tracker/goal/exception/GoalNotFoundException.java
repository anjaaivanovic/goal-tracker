package com.example.goal_tracker.goal.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GoalNotFoundException extends EntityNotFoundException {
    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public GoalNotFoundException() {
        super("Goal not found.");
    }
}