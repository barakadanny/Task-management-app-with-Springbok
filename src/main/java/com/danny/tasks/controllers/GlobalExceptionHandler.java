package com.danny.tasks.controllers;

import com.danny.tasks.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.danny.tasks.domain.dto.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * GlobalExceptionHandler is used to catch and handle exceptions
 * thrown anywhere in the application, especially in controller layers.
 *
 * The annotation @ControllerAdvice:
 * - Tells Spring this class contains global exception handling logic.
 * - It applies to all controllers in the application.
 *
 * Inside this class, we define one or more methods to handle different types of exceptions.
 * Each method is annotated with @ExceptionHandler(...).
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * This method handles all IllegalArgumentExceptions or subclasses of RuntimeException.
     *
     * The @ExceptionHandler tells Spring to run this method when a RuntimeException is thrown.
     *
     * Parameters:
     * - ex: the actual exception that was thrown.
     * - request: holds information about the current web request (e.g., URI).
     *
     * Steps inside the method:
     * 1. We build a new ErrorResponse object using the exception message and request details.
     * 2. We return it wrapped in a ResponseEntity with the appropriate HTTP status (400 BAD REQUEST).
     *
     * Why use ResponseEntity?
     * - It allows us to control both the response body (ErrorResponse) and the status code.
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleExceptions(
            RuntimeException ex, WebRequest request
    ){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
