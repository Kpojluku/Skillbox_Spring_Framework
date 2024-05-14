package ru.goltsov.education.web.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.goltsov.education.model.RoleType;

@Data
public class UserRequest {

    @NotBlank(message = "Имя пользователя должно быть заполнено!")
    @Size(min = 3, max = 30, message = "Имя должно быть между {min} и {max} символами")
    private String name;

    @NotBlank(message = "Логин пользователя должен быть заполнен!")
    private String username;
    private String password;

}
