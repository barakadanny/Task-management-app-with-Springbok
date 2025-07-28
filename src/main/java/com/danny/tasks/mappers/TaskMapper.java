package com.danny.tasks.mappers;

import com.danny.tasks.domain.dto.TaskDto;
import com.danny.tasks.domain.entities.Task;

/**
 * Mapper interface to convert between Task entity and TaskDto.
 *
 * Purpose:
 * - TaskDto is used to transfer data (especially between client and server layers).
 * - Task (entity) is the database model used with JPA/Hibernate.
 *
 * This interface defines two transformation methods:
 * - fromDto(TaskDto): Converts a TaskDto to a Task entity.
 * - toDto(Task): Converts a Task entity to a TaskDto.
 *
 * This mapping helps decouple the domain logic (entity) from the external-facing structure (DTO),
 * and is especially useful when you want to avoid exposing internal details of your entities.
 *
 * Connection:
 * [TaskDto] ⇄ [TaskMapper] ⇄ [Task]
 *
 * Implemented by: TaskMapperImpl
 */
public interface TaskMapper {

    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);
}
