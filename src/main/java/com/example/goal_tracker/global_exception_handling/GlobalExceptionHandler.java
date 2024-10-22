package com.example.goal_tracker.global_exception_handling;

import com.example.goal_tracker.auth.exception.UserAlreadyExistsException;
import com.example.goal_tracker.auth.exception.UserNotFoundException;
import com.example.goal_tracker.goal.exception.GoalNotFoundException;
import com.example.goal_tracker.goal.exception.NoAccessToResourceException;
import com.example.goal_tracker.goal.exception.TaskNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<String> errorMessages = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        String errorMessage = String.join(" ", errorMessages);

        return new ResponseEntity<>(
                new GlobalExceptionResponse(errorMessage, HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(GoalNotFoundException.class)
    public ResponseEntity<GlobalExceptionResponse>  handleGoalNotFoundException(GoalNotFoundException e) {
        return new ResponseEntity<>(
                new GlobalExceptionResponse(e.getMessage(), e.getStatus()),
                e.getStatus()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalExceptionResponse>  handleHttpMessageNotReadableException(HttpMessageNotReadableException  e) {
        return new ResponseEntity<>(
                new GlobalExceptionResponse(e.getMessage(), HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<GlobalExceptionResponse>  handleTaskNotFoundException(TaskNotFoundException e) {
        return new ResponseEntity<>(
                new GlobalExceptionResponse(e.getMessage(), e.getStatus()),
                e.getStatus()
        );
    }

    @ExceptionHandler(NoAccessToResourceException.class)
    public ResponseEntity<GlobalExceptionResponse>  handleNoPermissionException(NoAccessToResourceException e) {
        return new ResponseEntity<>(
                new GlobalExceptionResponse(e.getMessage(), e.getStatus()),
                e.getStatus()
        );
    }
}