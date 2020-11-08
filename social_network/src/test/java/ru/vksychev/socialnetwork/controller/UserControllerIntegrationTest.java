package ru.vksychev.socialnetwork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.vksychev.socialnetwork.dto.UserEditDto;
import ru.vksychev.socialnetwork.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Создание пользователя - корректный вызов")
    void createUserOk() throws Exception {
        UserEditDto dto = UserUtils.generateUserEditDto();

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновление данных пользователя - корректный вызов")
    void updateUserOK() throws Exception {
        UserEditDto dto = UserUtils.generateUserEditDto();

        UUID id = createUser(dto);

        dto = UserUtils.generateUserEditDto();
        mockMvc.perform(put("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("\"" + id + "\""));
    }

    @Test
    @DisplayName("Удаление пользователя - корректный вызов")
    void deleteUserOK() throws Exception {
        UserEditDto dto = UserUtils.generateUserEditDto();

        UUID id = createUser(dto);

        mockMvc.perform(delete("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("\"" + id + "\""));
    }

    @Test
    @DisplayName("Нахождение пользователя по id - корректный вызов")
    void findOneUserOK() throws Exception {
        UserEditDto dto = UserUtils.generateUserEditDto();

        UUID id = createUser(dto);

        dto.setId(id);
        mockMvc.perform(get("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(dto)));
    }


    @Test
    @Sql("BeforeFindAll.sql")
    @DisplayName("Нахождение всех пользователей- корректный вызов")
    void findAllUserOK() throws Exception {
        List<UserEditDto> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UserEditDto dto = UserUtils.generateUserEditDto();
            UUID id = createUser(dto);
            dto.setId(id);
            users.add(dto);
        }

        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(users.size()));

    }

    @Test
    @Sql("BeforeFindAll.sql")
    @DisplayName("Добавление друга - корректный вызов")
    void addFriend() throws Exception {
        UUID userId = createUser(UserUtils.generateUserEditDto());
        UUID friendId = createUser(UserUtils.generateUserEditDto());

        mockMvc.perform(post("/users/{userId}/friends?targetId={friendId}", userId, friendId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("\"" + friendId + "\""));

    }

    @Test
    @DisplayName("Удаление друга - корректный вызов")
    void deleteFriendOK() throws Exception {
        UUID userId = createUser(UserUtils.generateUserEditDto());
        UUID friendId = createUser(UserUtils.generateUserEditDto());

        mockMvc.perform(post("/users/{userId}/friends?targetId={friendId}", userId, friendId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("\"" + friendId + "\""));

        mockMvc.perform(get("/users/{userId}/friends", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(1));

        mockMvc.perform(delete("/users/{userId}/friends?targetId={friendId}", userId, friendId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("\"" + friendId + "\""));

        mockMvc.perform(get("/users/{userId}/friends", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(0));
    }

    @Test
    @DisplayName("Обновление данных пользователя - проверка на идемпотентность")
    void updateUserIsIdempotent() throws Exception {
        UserEditDto dto = UserUtils.generateUserEditDto();
        UUID id = createUser(dto);
        String response = "\"" + id + "\"";
        dto = UserUtils.generateUserEditDto();

        mockMvc.perform(put("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string(response));
        mockMvc.perform(put("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("Удаление данных пользователя - проверка на идемпотентность")
    void deleteUserIsIdempotent() throws Exception {
        UUID userId = UUID.randomUUID();
        String response = "\"" + userId + "\"";

        mockMvc.perform(delete("/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(response));
        mockMvc.perform(delete("/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("Удаление данных пользователя - проверка на идемпотентность")
    void deleteFriendIsIdempotent() throws Exception {
        UUID userId = createUser(UserUtils.generateUserEditDto());
        UUID friendId = createUser(UserUtils.generateUserEditDto());

        mockMvc.perform(post("/users/{userId}/friends?targetId={friendId}", userId, friendId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("\"" + friendId + "\""));

        mockMvc.perform(delete("/users/{userId}/friends?targetId={friendId}", userId, friendId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("\"" + friendId + "\""));

        mockMvc.perform(delete("/users/{userId}/friends?targetId={friendId}", userId, friendId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("\"" + friendId + "\""));

    }

    @Test
    @DisplayName("Получение несуществующего пользователя - некорректный вызов")
    void getNonExistingUser() throws Exception {
        UUID userId = UUID.randomUUID();

        mockMvc.perform(get("/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Удаление пользователя с друзьями - корректный вызов")
    void deleteUserWithFriends() throws Exception {
        UUID userId = createUser(UserUtils.generateUserEditDto());
        UUID friendId = createUser(UserUtils.generateUserEditDto());

        mockMvc.perform(post("/users/{userId}/friends?targetId={friendId}", userId, friendId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("\"" + friendId + "\""));

        mockMvc.perform(delete("/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("\"" + userId + "\""));
    }

    /**
     * Создание пользователя средствами MockMvc
     *
     * @param dto данные пользователя
     * @return id созданного пользователя
     * @throws Exception неудачный запрос
     */
    private UUID createUser(UserEditDto dto) throws Exception {
        String s = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        s = s.replace("\"", "");
        return UUID.fromString(s);
    }

}
