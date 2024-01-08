package ru.goltsov.education.controller;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.goltsov.education.AbstractTest;
import ru.goltsov.education.web.model.UserModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserControllerTest extends AbstractTest {

    @Test
    public void whenGetAllUsers_thenReturnListOfUsersFromDB() {

        var expectedData = List.of(
                new UserModel(FIRST_USER_ID, "first", "f@f.com"),
                new UserModel(SECOND_USER_ID, "second", "s@s.com")
        );

        webTestClient.get()
                .uri("/api/users")
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.get().uri("/api/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserModel.class)
                .hasSize(2)
                .contains(expectedData.toArray(UserModel[]::new));
    }

    @Test
    public void whenGetUserById_thenReturnUserFromDB() {
        var expectedData = new UserModel(FIRST_USER_ID, "first", "f@f.com");

        webTestClient.get().uri("/api/users/{id}", FIRST_USER_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserModel.class)
                .isEqualTo(expectedData);
    }

    @Test
    public void whenCreateUser_thenReturnCreatedUser() {
        StepVerifier.create(userRepository.count())
                .expectNext(2L)
                .expectComplete()
                .verify();

        UserModel user = new UserModel();
        user.setUsername("third");
        user.setEmail( "t@t.com");
        webTestClient.post().uri("/api/users")
                .body(Mono.just(user), UserModel.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserModel.class)
                .value(userModel -> {
                    assertNotNull(userModel.getId());
                });

        StepVerifier.create(userRepository.count())
                .expectNext(3L)
                .expectComplete()
                .verify();

    }

    @Test
    public void whenUpdateUser_thenReturnUpdatedUser() {

        UserModel requestUser = new UserModel();
        requestUser.setUsername("third");

        webTestClient.put().uri("/api/users/{id}", FIRST_USER_ID)
                .body(Mono.just(requestUser), UserModel.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserModel.class)
                .value(userModel -> {
                    assertEquals("third", userModel.getUsername());
                });

    }

    @Test
    public void whenDeleteUser_thenReturnNoContent() {

        webTestClient.delete().uri("/api/users/{id}", FIRST_USER_ID)
                .exchange()
                .expectStatus().isNoContent();

        StepVerifier.create(userRepository.count())
                .expectNext(1L)
                .expectComplete()
                .verify();
    }
}
