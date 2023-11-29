package ru.goltsov.education.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.goltsov.education.mapper.UserMapper;
import ru.goltsov.education.model.User;
import ru.goltsov.education.service.UserService;
import ru.goltsov.education.util.StringTestUtils;
import ru.goltsov.education.web.model.request.UserRequest;
import ru.goltsov.education.web.model.response.UserResponse;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    UserService service;
    @MockBean
    UserMapper mapper;
    @Autowired
    ObjectMapper objectMapper;
    private final String url = "/api/users";

    @Test
    void whenCreateUser_thenReturnNewUser() throws Exception {
        long id = 1;
        UserRequest userRequest = new UserRequest();
        userRequest.setName("User " + id);

        User user = new User();
        user.setId(id);
        user.setName("User " + id);

        when(mapper.requestToUser(userRequest)).thenReturn(user);
        when(service.save(user)).thenReturn(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(id);
        userResponse.setName("User " + id);

        when(mapper.userToResponse(user)).thenReturn(userResponse);

        String actualResponse = mockMvc.perform(
                        post(url)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRequest))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("/response/user/create_user_response.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

        // проверяет, что методы вызываются по одному разу
        verify(mapper, times(1)).requestToUser(userRequest);
        verify(service, times(1)).save(user);
        verify(mapper, times(1)).userToResponse(user);

    }

}
