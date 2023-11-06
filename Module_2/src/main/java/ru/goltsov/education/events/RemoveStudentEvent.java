package ru.goltsov.education.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RemoveStudentEvent extends ApplicationEvent {

    private final String message;

    public RemoveStudentEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

}
