package com.danny.tasks.services.impl;

import com.danny.tasks.domain.entities.TaskList;
import com.danny.tasks.repositories.TaskListRepository;
import com.danny.tasks.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
}
