package com.danny.tasks.mappers.impl;

import com.danny.tasks.domain.dto.TaskDto;
import com.danny.tasks.domain.entities.Task;
import com.danny.tasks.mappers.TaskMapper;
import org.springframework.stereotype.Component;

/**
 * Implementation of TaskMapper interface.
 *
 * This class handles the actual transformation logic between TaskDto and Task.
 * - fromDto: Builds a Task entity using the values from TaskDto.
 * - toDto: Builds a TaskDto using values from Task.
 *
 * Notes:
 * - This mapping is manual, giving full control over which fields are copied and how.
 * - Fields like `createdAt`, `updatedAt`, or `assignedUser` are currently set to null;
 *   these can be customized or injected later depending on business logic.
 *
 * Spring Integration:
 * - Annotated with @Component so that Spring can automatically detect and register this class
 *   as a Spring-managed bean. This allows us to inject and reuse the mapper wherever needed
 *   (like in services or controllers) using dependency injection.
 *
 * Learning Tip:
 * I am using this as a practice to understand how DTOs and
 * Entities stay in sync while remaining separate.
 */


@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task fromDto(TaskDto taskDto) {
        return new Task(
                taskDto.id(),
                taskDto.title(),
                taskDto.description(),
                taskDto.dueDate(),
                taskDto.status(),
                taskDto.priority(),
                null,
                null,
                null
        );
    }

    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus()
        );
    }
}
