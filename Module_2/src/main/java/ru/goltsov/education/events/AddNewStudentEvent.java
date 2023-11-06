package ru.goltsov.education.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AddNewStudentEvent extends ApplicationEvent {

    private final String message;

    public AddNewStudentEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

}
