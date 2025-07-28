package com.danny.tasks.mappers;

import com.danny.tasks.domain.dto.TaskListDto;
import com.danny.tasks.domain.entities.TaskList;

public interface TaskListMapper {
    TaskList fromDTO(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}
