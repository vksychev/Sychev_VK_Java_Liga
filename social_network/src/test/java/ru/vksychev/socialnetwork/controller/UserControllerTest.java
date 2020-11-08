package ru.vksychev.socialnetwork.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import ru.vksychev.socialnetwork.dto.UserEditDto;
import ru.vksychev.socialnetwork.dto.UserListDto;
import ru.vksychev.socialnetwork.exception.UserNotFoundException;
import ru.vksychev.socialnetwork.service.UserService;
import ru.vksychev.socialnetwork.utils.UserUtils;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserControllerTest {

    UserController userController;

    @Mock
    UserService userService;


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userService);
    }

    @Test
    @DisplayName("Получение всех пользователей")
    public void findAllTest() {
        PageImpl<UserListDto> page = Mockito.mock(PageImpl.class);
        Mockito.when(userService.findAll(any(), any())).thenReturn(page);
        userController.findAll(any(), any());

        verify(userService, times(1)).findAll(any(), any());
    }

    @Test
    @DisplayName("Получение всех пользователей")
    public void findOneTest() throws UserNotFoundException {
        Mockito.when(userService.findOne(any())).thenReturn(new UserEditDto());
        userController.findOne(UUID.randomUUID());

        verify(userService, times(1)).findOne(any());
    }

    @Test
    @DisplayName("Получение друзей пользователя по id")
    public void findFriendsTest() throws UserNotFoundException {
        PageImpl<UserListDto> page = Mockito.mock(PageImpl.class);
        Mockito.when(userService.findFriends(any(), any())).thenReturn(page);
        userController.findFriends(any(), any());

        verify(userService, times(1)).findFriends(any(), any());
    }

    @Test
    @DisplayName("Создание пользователя")
    public void createTest() {
        Mockito.when(userService.create(any())).thenReturn(UUID.randomUUID());
        userController.create(UserUtils.generateUserEditDto());

        verify(userService, times(1)).create(any());
    }

    @Test
    @DisplayName("Добавление друга")
    public void addFriendTest() throws UserNotFoundException {
        Mockito.when(userService.addFriend(any(), any())).thenReturn(UUID.randomUUID());
        userController.addFriend(UUID.randomUUID(), UUID.randomUUID());

        verify(userService, times(1)).addFriend(any(), any());
    }

    @Test
    @DisplayName("Обновление пользователя")
    public void updateTest() throws UserNotFoundException {
        Mockito.when(userService.update(any(), any())).thenReturn(UUID.randomUUID());
        userController.update(UUID.randomUUID(), UserUtils.generateUserEditDto());

        verify(userService, times(1)).update(any(), any());
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void deleteTest() {
        Mockito.when(userService.delete(any())).thenReturn(UUID.randomUUID());
        userController.delete(UUID.randomUUID());

        verify(userService, times(1)).delete(any());
    }

    @Test
    @DisplayName("Удаление друга")
    public void deleteFriendTest() throws UserNotFoundException {
        Mockito.when(userService.addFriend(any(), any())).thenReturn(UUID.randomUUID());
        userController.deleteFriend(UUID.randomUUID(), UUID.randomUUID());

        verify(userService, times(1)).deleteFriend(any(), any());
    }

}
