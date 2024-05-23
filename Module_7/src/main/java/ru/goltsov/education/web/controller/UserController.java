package ru.goltsov.education.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.goltsov.education.entity.RoleType;
import ru.goltsov.education.entity.User;
import ru.goltsov.education.exception.EntityNotFoundException;
import ru.goltsov.education.mapper.UserMapper;
import ru.goltsov.education.service.UserService;
import ru.goltsov.education.web.model.UserModel;

import java.security.Principal;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public Flux<UserModel> getAllUsers(Mono<Principal> principalMono) {
//        Mono<User> userNotFound = principalMono.map(Principal::getName)
//                .flatMap(userService::findByUsername)
//                .switchIfEmpty(Mono.error(new EntityNotFoundException("Not authorized user")));

        return userService.findAll().map(userMapper::userToUserModel);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public Mono<ResponseEntity<UserModel>> getById(@PathVariable String id) {
        return userService.findById(id)
                .map(userMapper::userToUserModel)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<UserModel>> createUser(@RequestBody UserModel model, @RequestParam RoleType roleType) {
        return userService.save(userMapper.userModelToUser(model), roleType)
                .map(userMapper::userToUserModel)
                .map(ResponseEntity::ok);
    }


    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public Mono<ResponseEntity<UserModel>> updateUser(@PathVariable String id, @RequestBody UserModel model) {
        return userService.update(id, userMapper.userModelToUser(model))
                .map(userMapper::userToUserModel)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String id) {
        return userService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
