package ru.goltsov.education.web.model;

import lombok.Data;
import ru.goltsov.education.entity.User;

import java.util.Set;

@Data
public class TaskResponse {

    private String id;
    private String name;
    private String description;
    private String createdAt;
    private String updatedAt;
    private String status;
    private User author;
    private User assignee;
    private Set<User> observers;

}
