package com.danny.tasks.controllers;

import com.danny.tasks.domain.dto.TaskDto;
import com.danny.tasks.domain.entities.Task;
import com.danny.tasks.mappers.TaskMapper;
import com.danny.tasks.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/task-list/{task_list_id}/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDto> getTasks(@PathVariable("task_list_id")UUID taskListId){
        return taskService.listTask(taskListId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskDto newTask(@PathVariable("task_list_id")UUID taskListId, @RequestBody TaskDto taskDto){
        Task createdTask = taskService.createTask(
                taskListId,
                taskMapper.fromDto(taskDto)
        );

        return taskMapper.toDto(createdTask);
    }

    @GetMapping(path="/{task_id}")
    public Optional<TaskDto> getTask(
            @PathVariable("task_list_id")UUID taskListId,
            @PathVariable("task_id") UUID taskId
            ){
        return taskService.getTask(taskListId, taskId).map(taskMapper::toDto);
    }

    @PutMapping(path="/{task_id}")
    public TaskDto updateTask(
            @PathVariable("task_list_id")UUID taskListId,
            @PathVariable("task_id") UUID taskId,
            @RequestBody TaskDto taskDto
    ){
        Task updatedTask = taskService.updateTask(
                taskListId,
                taskId,
                taskMapper.fromDto(taskDto)
                );
        return taskMapper.toDto(updatedTask);
    }
}
