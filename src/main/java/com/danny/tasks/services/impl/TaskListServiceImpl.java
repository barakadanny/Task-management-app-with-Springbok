package com.danny.tasks.services.impl;

import com.danny.tasks.domain.entities.TaskList;
import com.danny.tasks.exceptions.ResourceNotFoundException;
import com.danny.tasks.repositories.TaskListRepository;
import com.danny.tasks.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {
    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> listTaskList() {
        return taskListRepository.findAll();
    }
    /**
     * Creates and persists a new TaskList while explicitly controlling which fields are stored.
     *
     * Instead of saving the incoming object directly, we construct a new TaskList to:
     * - Ensure the ID is null (preventing accidental updates)
     * - Prevent unintended persistence of nested tasks or other fields
     * - Set creation and update timestamps ourselves (not relying on client input)
     * - Protect against invalid or malicious input data
     *
     * This is a defensive programming practice that keeps our persistence layer safe and predictable.
     */

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if (null != taskList.getId()){
            throw new IllegalArgumentException("Task list already has an ID!");
        }

        if (taskList.getTitle() == null || taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        LocalDateTime now = LocalDateTime.now();
        return taskListRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now
        ));
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findById(id);
    }

    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {

        TaskList existingTaskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new ResourceNotFoundException("Task list with ID " + taskListId + " not found"));

        // Only update the title if a new one is provided
        if (taskList.getTitle() != null && !taskList.getTitle().isBlank()) {
            existingTaskList.setTitle(taskList.getTitle());
        }

        // Only update the description if a new one is provided
        if (taskList.getDescription() != null) {
            existingTaskList.setDescription(taskList.getDescription());
        }
        existingTaskList.setUpdated(LocalDateTime.now());
        return taskListRepository.save(existingTaskList);
    }

    @Override
    public void deleteTaskList(UUID taskListId) {
        boolean exist = taskListRepository.existsById(taskListId);

        if(!exist){
            throw new ResourceNotFoundException("Task list with ID " + taskListId + " not found");
        }
        taskListRepository.deleteById(taskListId);
    }
}
