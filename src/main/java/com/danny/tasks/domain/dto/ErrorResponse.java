package com.danny.tasks.domain.dto;

/**
 * ErrorResponse is a DTO (Data Transfer Object) used to structure
 * and send error information to the client in a clean and predictable format.
 *
 * We use a Java `record` here because:
 * - It is perfect for immutable, data-only objects.
 * - It auto-generates a constructor, getters, equals, hashCode, and toString.
 * - This keeps the code short and clean.
 *
 * Fields:
 * - status: the HTTP status code (e.g., 400, 404, 500).
 * - message: a human-readable message about the error.
 * - details: extra info such as the path where the error occurred.
 *
 * This record is typically used when we want to return errors from our global error handler.
 */
public record ErrorResponse(
        int status,
        String message,
        String details
) {
    // No need to define constructor or getters manually. Records do it for us.
}
