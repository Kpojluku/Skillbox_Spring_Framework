package ru.goltsov.education.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.goltsov.education.entity.RoleType;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private String id;
    private String username;
    private String password;
    private String email;
    private Set<RoleType> roles;


}
