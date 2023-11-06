package ru.goltsov.education;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Student {

    int id;
    private String firstName;
    private String lastName;
    private int age;

}
