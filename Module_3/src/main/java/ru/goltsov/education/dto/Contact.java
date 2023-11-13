package ru.goltsov.education.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class Contact {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

}
