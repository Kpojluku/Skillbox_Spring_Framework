package ru.goltsov.education.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.goltsov.education.entity.Task;
import ru.goltsov.education.web.model.TaskRequest;
import ru.goltsov.education.web.model.TaskResponse;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapInstantToString")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapInstantToString")
    TaskResponse taskToTaskResponse(Task task);

    Task taskRequestToTask(TaskRequest taskRequest);

    @Named("mapInstantToString")
    static String mapInstantToString(Instant instant) {
        if (instant != null) {
            return instant.atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm"));
        }
        return null;
    }


}
