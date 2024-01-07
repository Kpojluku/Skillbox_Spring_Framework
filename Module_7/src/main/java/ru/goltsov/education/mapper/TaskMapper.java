package ru.goltsov.education.mapper;

import org.mapstruct.Mapper;
import ru.goltsov.education.entity.Task;
import ru.goltsov.education.web.model.TaskRequest;
import ru.goltsov.education.web.model.TaskResponse;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskResponse taskToTaskResponse(Task task);
    Task taskRequestToTask(TaskRequest taskRequest);


}
