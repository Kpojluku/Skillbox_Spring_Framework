package ru.goltsov.education;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.goltsov.education.events.AddNewStudentEvent;
import ru.goltsov.education.events.RemoveStudentEvent;

import java.util.List;

@ShellComponent
public class StudentRegisterShell {

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    private StudentRegister studentRegister;

    @Autowired
    public StudentRegisterShell(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    @ShellMethod
    public void add(@ShellOption(value = "i") String firstName,
                    @ShellOption(value = "f") String lastName,
                    String age) {

        Student newStudent = studentRegister.add(firstName, lastName, age);
        if (newStudent != null) {
            eventPublisher.publishEvent(new AddNewStudentEvent(this, newStudent.toString()));
        }
    }

    @ShellMethod
    public String view() {
        List<Student> list = studentRegister.getList();
        return list.toString();
    }

    @ShellMethod
    public void purge() {
        studentRegister.clear();
    }

    @ShellMethod
    public void remove(String id) {
        if (studentRegister.remove(id)) {
            eventPublisher.publishEvent(new RemoveStudentEvent(this, id));
        }
    }


}
