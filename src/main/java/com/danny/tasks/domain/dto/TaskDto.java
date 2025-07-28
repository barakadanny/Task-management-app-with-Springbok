package com.danny.tasks.domain.dto;

import com.danny.tasks.domain.entities.TaskPriority;
import com.danny.tasks.domain.entities.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
/**
 * TaskDto is a Java record used as a Data Transfer Object (DTO).
 *
 * A record is a concise way to create an immutable data class.
 * It automatically provides:
 *  - A constructor
 *  - Getters (accessible via field names like id(), title(), etc.)
 *  - equals(), hashCode(), and toString() methods
 *
 * This is ideal for DTOs, which are simple containers for data passed
 * between layers (e.g., controller <-> service <-> client).
 *
 * Records promote immutability, reduce boilerplate code, and improve readability.
 */

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) {
}
