package ru.goltsov.education.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskStatus {

    TODO("todo"),
    IN_PROGRESS("in_progress"),
    DONE("done");

    private final String name;

}
