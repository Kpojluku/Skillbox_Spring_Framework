package ru.goltsov.education.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.goltsov.education.service.UserService;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app.security", name = "type", havingValue = "db")
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final UserService userService;


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userService.findByUsername(username)
//                .flatMap(Mono::just)
                .map(AppUserPrincipal::new);
    }
}
