package com.example.goal_tracker.global_exception_handling;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class GlobalExceptionResponse {
    private String message;
    private HttpStatus httpStatus;
}