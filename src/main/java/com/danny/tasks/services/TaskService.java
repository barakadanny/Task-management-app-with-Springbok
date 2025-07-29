package com.danny.tasks.services;

import com.danny.tasks.domain.entities.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    List<Task> listTask(UUID taskListId);
}
