package com.danny.tasks.controllers;

import com.danny.tasks.domain.dto.TaskDto;
import com.danny.tasks.mappers.TaskMapper;
import com.danny.tasks.services.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    public List<TaskDto> getTask(@PathVariable("task_list_id")UUID taskListId){
        return taskService.listTask(taskListId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }
}
