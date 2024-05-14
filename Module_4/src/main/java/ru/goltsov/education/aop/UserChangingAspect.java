package ru.goltsov.education.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.goltsov.education.exception.AccessDeniedException;
import ru.goltsov.education.model.User;
import ru.goltsov.education.service.UserService;
import ru.goltsov.education.web.model.request.UserRequest;

import java.util.Objects;

import static ru.goltsov.education.utils.SecurityUtils.isUserRoleOnly;

@Aspect
@Component
public class UserChangingAspect {

    @Autowired
    private UserService userService;

    @Before("execution(* ru.goltsov.education.web.controller.UserController.findById(..)) && args(userId, userDetails)")
    public void checkUserPermissionForFindById(Long userId, UserDetails userDetails) {
        accessCheck(userDetails, userId, "Пользователь, с ролью ROLE_USER, может получить только информацию о себе");
    }

    @Before("execution(* ru.goltsov.education.web.controller.UserController.updateUser(..)) && args(userId, request, userDetails)")
    public void checkUserPermissionForUpdateUser(Long userId, UserRequest request, UserDetails userDetails) {
        accessCheck(userDetails, userId, "Пользователь, с ролью ROLE_USER, может обновить только свои данные");
    }

    @Before("execution(* ru.goltsov.education.web.controller.UserController.deleteUser(..)) && args(userId, userDetails)")
    public void checkUserPermissionForDeleteUser(Long userId, UserDetails userDetails) {
        accessCheck(userDetails, userId, "Пользователь, с ролью ROLE_USER, может удалить только свои данные");
    }

    private void accessCheck(UserDetails userDetails, Long userId, String message) {
        if (isUserRoleOnly(userDetails)) {
            User byUsername = userService.findByUsername(userDetails.getUsername());
            if (!Objects.equals(byUsername.getId(), userId)) {
                throw new AccessDeniedException(message);
            }
        }
    }

}
