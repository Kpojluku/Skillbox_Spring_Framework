package ru.goltsov.education.web.model;

import lombok.Data;

import java.util.Set;

@Data
public class TaskRequest {

    private String name;
    private String description;
    private String status;
    private String authorId;
    private String assigneeId;
    private Set<String> observerIds;


}
