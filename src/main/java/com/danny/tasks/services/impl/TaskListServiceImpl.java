package com.danny.tasks.services.impl;

import com.danny.tasks.domain.entities.TaskList;
import com.danny.tasks.repositories.TaskListRepository;
import com.danny.tasks.services.TaskListService;
import org.springframework.stereotype.Service;

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
}
