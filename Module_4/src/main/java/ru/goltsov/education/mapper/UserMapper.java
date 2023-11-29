package ru.goltsov.education.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.goltsov.education.model.User;
import ru.goltsov.education.web.model.response.UserListResponse;
import ru.goltsov.education.web.model.request.UserRequest;
import ru.goltsov.education.web.model.response.UserResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User requestToUser(UserRequest request);

    @Mapping(source = "userId", target = "id")
    User requestToUser(Long userId, UserRequest request);

    UserResponse userToResponse(User user);

    default UserListResponse userListToUserListResponse(List<User> users) {
        UserListResponse response = new UserListResponse();
        response.setUsers(users.stream().map(this::userToResponse).toList());
        return response;
    }

}
