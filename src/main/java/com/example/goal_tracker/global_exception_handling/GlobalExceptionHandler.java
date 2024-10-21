package com.example.goal_tracker.global_exception_handling;

import com.example.goal_tracker.auth.exception.UserAlreadyExistsException;
import com.example.goal_tracker.auth.exception.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<GlobalExceptionResponse>  handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return new ResponseEntity<>(
            new GlobalExceptionResponse(e.getMessage(), e.getStatus()),
            e.getStatus()
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<GlobalExceptionResponse>  handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(
            new GlobalExceptionResponse(e.getMessage(), e.getStatus()),
            e.getStatus()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GlobalExceptionResponse>  handleBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(
             new GlobalExceptionResponse("Invalid email or password.", HttpStatus.BAD_REQUEST),
             HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<GlobalExceptionResponse> handleExpiredJwtException(ExpiredJwtException e) {
        return new ResponseEntity<>(
                new GlobalExceptionResponse("JWT token expired.", HttpStatus.UNAUTHORIZED),
                HttpStatus.UNAUTHORIZED
        ) ;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalExceptionResponse> handleException(Exception e) {
        return new ResponseEntity<>(
                new GlobalExceptionResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR
        ) ;
    }
}