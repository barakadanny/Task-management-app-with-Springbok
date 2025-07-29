package com.danny.tasks.services;

import com.danny.tasks.domain.entities.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> listTask(UUID taskListId);
    Task createTask(UUID taskListId, Task task);
    Optional<Task> getTask(UUID taskListId, UUID taskId);
    void deleteTask(UUID taskListId, UUID taskId);
    Task updateTask(UUID taskListId,UUID taskId, Task task);
}
