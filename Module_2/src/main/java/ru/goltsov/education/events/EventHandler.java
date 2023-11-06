package ru.goltsov.education.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

    @EventListener
    public void handleAddNewStudentEventEvent(AddNewStudentEvent event) {
        // Обработка события
        // Как переключиться в utf-8
//        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));

        System.out.println("Добавлен студент: " + event.getMessage());
    }

    @EventListener
    public void handleRemoveStudentEventEvent(RemoveStudentEvent event) {
        System.out.println("Removed student: " + event.getMessage());
    }

}
