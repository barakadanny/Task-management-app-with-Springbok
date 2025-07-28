package com.danny.tasks.mappers.impl;

import com.danny.tasks.domain.dto.TaskListDto;
import com.danny.tasks.domain.entities.Task;
import com.danny.tasks.domain.entities.TaskList;
import com.danny.tasks.domain.entities.TaskStatus;
import com.danny.tasks.mappers.TaskListMapper;
import com.danny.tasks.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the TaskListMapper interface.
 *
 * This class is responsible for converting between:
 * - TaskListDto (data sent to or received from the frontend or API)
 * - TaskList (the actual internal domain entity used in the application)
 *
 * The mapping is done manually for learning purposes, so I can clearly see
 * how data is transformed between different layers of the app.
 *
 * Notes:
 * - We use @Component so Spring can automatically detect and manage this class.
 * - This class depends on another mapper, TaskMapper, to map nested task objects.
 * - It handles both directions: from DTO → Entity, and Entity → DTO.
 */

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    // Constructor-based dependency injection for TaskMapper
    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    /**
     * Converts a TaskListDto object into a TaskList entity.
     * This is used when we receive data from the client (e.g., POST or PUT).
     *
     * It also maps the list of tasks (inside the DTO) to actual Task entities.
     * If the task list is missing (null), it safely avoids errors using Optional.
     */
    @Override
    public TaskList fromDTO(TaskListDto taskListDto) {
        return new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                // If tasks are not null, map each TaskDto to a Task using taskMapper
                Optional.ofNullable(taskListDto.tasks())
                        .map(tasks -> tasks.stream().map(
                                taskMapper::fromDto) // use :: as shorthand for (task -> taskMapper.fromDto(task))
                                .toList()
                        ).orElse(null),
                null,
                null

        );
    }

    /**
     * Converts a TaskList entity into a TaskListDto object.
     * This is used when sending data back to the client (e.g., via GET API).
     *
     * We also include:
     * - The number of tasks in the list
     * - The progress of the task list (how many are completed)
     * - A list of TaskDto (mapped using taskMapper)
     */
    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),

                // Count number of tasks (if not null)
                Optional.ofNullable(taskList.getTasks())
                        .map(List::size) // Get the size of the list
                        .orElse(0), // If list is null, return 0

                // Calculate the percentage of completed tasks
                calculateTaskListProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::toDto) // convert each Task to TaskDto
                                .toList()
                        ).orElse(null)
        );
    }

    /**
     * Calculates how much of the task list is completed (in % form).
     * Example: if 2 out of 4 tasks are CLOSED, progress = 0.5 (or 50%).
     *
     * Step-by-step:
     * - Check if tasks list is null → return null if so
     * - Count how many tasks are CLOSED using a filter
     * - Divide closed task count by total task count to get a decimal (e.g. 0.5 = 50%)
     */
    private Double calculateTaskListProgress(List<Task> tasks){
        if(null == tasks){
            return null;
        }

        // Count tasks where status is CLOSED
        long closedTaskCount = tasks.stream()
                .filter(task -> TaskStatus.CLOSED == task.getStatus())
                .count();

        // Calculate progress as a decimal (e.g., 3/5 = 0.6)
        return (double) closedTaskCount / tasks.size();
    }
}
