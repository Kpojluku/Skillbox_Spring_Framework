package ru.goltsov.education.mapper;

import ru.goltsov.education.entity.Task;
import ru.goltsov.education.entity.TaskStatus;
import ru.goltsov.education.web.model.TaskRequest;
import ru.goltsov.education.web.model.TaskResponse;

import java.time.format.DateTimeFormatter;

public abstract class TaskMapperDelegate implements TaskMapper {

    @Override
    public Task taskRequestToTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(TaskStatus.valueOf(taskRequest.getStatus()));
        task.setAuthorId(taskRequest.getAuthorId());
        task.setAssigneeId(taskRequest.getAssigneeId());
        task.setObserverIds(taskRequest.getObserverIds());

        return task;
    }

    @Override
    public TaskResponse taskToTaskResponse(Task task){
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setName(task.getName());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setStatus(task.getStatus().name());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        taskResponse.setUpdatedAt(dateFormat.format(task.getUpdatedAt()));
        taskResponse.setCreatedAt(dateFormat.format(task.getCreatedAt()));
        taskResponse.setAuthor(task.getAuthor());
        taskResponse.setAssignee(task.getAssignee());
        taskResponse.setObservers(task.getObservers());
        return taskResponse;
    }

}
