package ru.goltsov.education.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.goltsov.education.mapper.UserMapper;
import ru.goltsov.education.model.Role;
import ru.goltsov.education.model.RoleType;
import ru.goltsov.education.model.User;
import ru.goltsov.education.service.UserService;
import ru.goltsov.education.web.model.response.UserListResponse;
import ru.goltsov.education.web.model.request.UserRequest;
import ru.goltsov.education.web.model.response.UserResponse;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;


    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserListResponse> findAll(Pageable pageable) {
        return ResponseEntity.ok(
                userMapper.userListToUserListResponse(userService.findAll(pageable).stream().toList())
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<UserResponse> findById(@PathVariable("id") Long userId,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                userMapper.userToResponse(userService.findById(userId))
        );
    }


    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request, @RequestParam RoleType roleType) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                userMapper.userToResponse(
                        userService.save(
                                userMapper.requestToUser(request),
                                Role.from(roleType)
                        )
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long userId, @RequestBody UserRequest request,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                userMapper.userToResponse(
                        userService.update(
                                userMapper.requestToUser(userId, request)
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long userId, @AuthenticationPrincipal UserDetails userDetails) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

}
