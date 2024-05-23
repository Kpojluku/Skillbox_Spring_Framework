package ru.goltsov.education.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.goltsov.education.entity.RoleType;
import ru.goltsov.education.entity.User;
import ru.goltsov.education.repository.UserRepository;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
//                .orElseThrow(() -> new RuntimeException("Username not found: " + username));
    }

    public Mono<User> save(User user, RoleType roleType) {

        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(roleType));
        return userRepository.save(user);
    }

    public Mono<User> update(String id, User user) {
        return findById(id).flatMap(userForUpdate -> {
            if (StringUtils.hasText(user.getUsername())) {
                userForUpdate.setUsername(user.getUsername());
            }
            if (StringUtils.hasText(user.getEmail())) {
                userForUpdate.setEmail(user.getEmail());
            }
            return userRepository.save(userForUpdate);
        });
    }


    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }


}
