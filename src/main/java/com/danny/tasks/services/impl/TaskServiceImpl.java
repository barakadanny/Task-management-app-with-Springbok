package com.danny.tasks.services.impl;

import com.danny.tasks.domain.entities.Task;
import com.danny.tasks.domain.entities.TaskList;
import com.danny.tasks.domain.entities.TaskPriority;
import com.danny.tasks.domain.entities.TaskStatus;
import com.danny.tasks.repositories.TaskListRepository;
import com.danny.tasks.repositories.TaskRepository;
import com.danny.tasks.services.TaskService;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTask(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Override
    public Task createTask(UUID taskListId, Task task) {
        if(null!= task.getId()){
            throw new IllegalArgumentException("Task already has an ID!");
        }

        if(task.getTitle() == null || task.getTitle().isBlank()){
            throw new IllegalArgumentException("A task Must have a title");
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = Optional.ofNullable(task.getStatus()).orElse(TaskStatus.OPEN);

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(()-> new IllegalArgumentException("Invalid Task List ID provided"));
        LocalDateTime now = LocalDateTime.now();

        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now
        );

        return taskRepository.save(taskToSave);
    }
}
