package com.danny.tasks.controllers;

import com.danny.tasks.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.danny.tasks.domain.dto.ErrorResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.stream.Collectors;

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

    /**
     * Global Exception Handler for Enum Parsing Errors in JSON requests.
     *
     * This method catches cases where Spring (via Jackson) fails to convert a String value
     * into an enum (like TaskStatus or TaskPriority) during request deserialization.
     *
     * For example, if the user sends `"priority": "HIG"` instead of `"HIGH"`,
     * Jackson will throw a `HttpMessageNotReadableException`, caused by an `InvalidFormatException`.
     *
     * This handler:
     * 1. Detects if the error comes from trying to convert to an enum.
     * 2. Extracts the exact field name and the invalid value.
     * 3. Builds a user-friendly error message showing allowed values.
     * 4. Falls back to a generic "bad request" error for other deserialization issues.
     *
     * The result is returned as an `ErrorResponse` with HTTP 400 (Bad Request).
     */

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleEnumParsingError(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        // Get the root cause of the deserialization error
        Throwable cause = ex.getCause();

        // Check if it's an InvalidFormatException caused by an invalid enum value
        if (cause instanceof InvalidFormatException ife && ife.getTargetType().isEnum()) {
            // Get the enum class (e.g., TaskPriority or TaskStatus)
            Class<?> enumClass = ife.getTargetType();

            // Extract the field name that caused the error, or "unknown" if not found
            String fieldName = ife.getPath().isEmpty() ? "unknown" : ife.getPath().get(0).getFieldName();

            // Build a comma-separated string of allowed enum values
            String allowedValues = Arrays.stream(enumClass.getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));

            // Create a friendly error message for the client
            String message = String.format(
                    "Invalid value '%s' for field '%s'. Allowed values are: [%s]",
                    ife.getValue(),
                    fieldName,
                    allowedValues
            );

            // Return the custom ErrorResponse with HTTP 400
            ErrorResponse error = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    message,
                    "uri=" + request.getRequestURI()
            );

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        // Fallback: handle other general JSON deserialization issues (e.g., bad date format)
        ErrorResponse fallbackError = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid request format.",
                "uri=" + request.getRequestURI()
        );

        return new ResponseEntity<>(fallbackError, HttpStatus.BAD_REQUEST);
    }
}
