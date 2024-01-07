package ru.goltsov.education.mapper;

import org.mapstruct.Mapper;
import ru.goltsov.education.entity.User;
import ru.goltsov.education.web.model.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userModelToUser(UserModel userModel);

    UserModel userToUserModel(User user);


}
