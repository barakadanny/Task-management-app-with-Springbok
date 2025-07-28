package com.danny.tasks.domain.dto;

import java.util.List;
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
public record TaskListDto(
        UUID id,
        String title,
        String description,
        Integer count,
        Double progress,
        List<TaskDto> tasks
) {
}
