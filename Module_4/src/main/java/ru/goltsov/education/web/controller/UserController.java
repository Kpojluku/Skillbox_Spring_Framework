package ru.goltsov.education.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.goltsov.education.mapper.UserMapper;
import ru.goltsov.education.service.UserService;
import ru.goltsov.education.web.model.response.UserListResponse;
import ru.goltsov.education.web.model.request.UserRequest;
import ru.goltsov.education.web.model.response.UserResponse;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;


    @GetMapping
    public ResponseEntity<UserListResponse> findAll(Pageable pageable){
        return ResponseEntity.ok(
                userMapper.userListToUserListResponse(userService.findAll(pageable).stream().toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable("id") Long clientId) {
        return ResponseEntity.ok(
                userMapper.userToResponse(userService.findById(clientId))
        );
    }


    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                userMapper.userToResponse(
                        userService.save(
                                userMapper.requestToUser(request)
                        )
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long userId, @RequestBody UserRequest request) {
        return ResponseEntity.ok(
                userMapper.userToResponse(
                        userService.update(
                                userMapper.requestToUser(userId, request)
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

}
