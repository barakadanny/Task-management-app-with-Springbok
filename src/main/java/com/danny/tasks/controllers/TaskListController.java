package com.danny.tasks.controllers;

import com.danny.tasks.domain.dto.TaskListDto;
import com.danny.tasks.domain.entities.TaskList;
import com.danny.tasks.mappers.TaskListMapper;
import com.danny.tasks.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/task-lists")
public class TaskListController {

    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;

    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    @GetMapping
    public List<TaskListDto> getTaskList(){
        return taskListService.listTaskList()
                .stream()
                .map(taskListMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskListDto newTaskList(@RequestBody TaskListDto taskListDto){
        TaskList createdTaskList = taskListService.createTaskList(
                taskListMapper.fromDTO(taskListDto)
        );
        return  taskListMapper.toDto(createdTaskList);
    }

}
