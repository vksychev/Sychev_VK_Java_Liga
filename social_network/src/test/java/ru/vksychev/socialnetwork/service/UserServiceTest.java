package ru.vksychev.socialnetwork.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.vksychev.socialnetwork.domain.User;
import ru.vksychev.socialnetwork.dto.UserEditDto;
import ru.vksychev.socialnetwork.dto.UserListDto;
import ru.vksychev.socialnetwork.repository.UserRepository;
import ru.vksychev.socialnetwork.service.filter.UserFilter;
import ru.vksychev.socialnetwork.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    UserService userService;

    @Mock
    UserRepository repository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(repository);
    }

    @Test
    @DisplayName("Получение всех пользователей")
    public void findAllTest() {

        Pageable pageable = PageRequest.of(0, 20, Sort.unsorted());
        List<User> users = new ArrayList<>();
        users.add(UserUtils.generateUser());
        users.add(UserUtils.generateUser());
        Page<User> page = new PageImpl<>(users, pageable, 2);
        UserFilter filter = new UserFilter();

        Mockito.when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        Page<UserListDto> userPage = userService.findAll(filter, pageable);
        List<UserListDto> userListDtos = userPage.getContent();

        for (int i = 0; i < userListDtos.size(); i++) {
            UserUtils.assertEquals(users.get(i), userListDtos.get(i));
        }

        Assertions.assertEquals(userListDtos.size(), 2);
        verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Получение пользователя по id")
    public void findOneTest() {
        User user = UserUtils.generateUser();
        Mockito.when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        UserEditDto editDto = userService.findOne(user.getId());

        UserUtils.assertEquals(user, editDto);
        verify(repository, times(1)).findById(user.getId());
    }

    @Test
    @DisplayName("Получение друзей пользователя по id")
    public void findFriendsTest() {
        User user = UserUtils.generateUser();
        List<User> users = new ArrayList<>();
        users.add(UserUtils.generateUser());
        users.add(UserUtils.generateUser());
        user.getFriends().addAll(users);

        Pageable pageable = PageRequest.of(0, 20, Sort.unsorted());
        Mockito.when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        List<UserListDto> friends = userService.findFriends(user.getId(), pageable).getContent();

        for (int i = 0; i < friends.size(); i++) {
            UserUtils.assertEquals(users.get(i), friends.get(i));
        }

        Assertions.assertEquals(friends.size(), 2);
        verify(repository, times(1)).findById(user.getId());
    }

    @Test
    @DisplayName("Создание пользователя")
    public void createTest() {
        User user = UserUtils.generateUser();
        UserEditDto editDto = userService.convertToEditDto(user);
        Mockito.when(repository.save(any(User.class))).thenReturn(user);
        UUID createdId = userService.create(editDto);

        Assertions.assertEquals(createdId, user.getId());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Создание пользователя")
    public void addFriendTest() {
        User user = UserUtils.generateUser();
        User friend = UserUtils.generateUser();
        Mockito.when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(repository.findById(friend.getId())).thenReturn(Optional.of(friend));
        Mockito.when(repository.save(any(User.class))).thenReturn(user);
        UUID friendId = userService.addFriend(user.getId(), friend.getId());


        Assertions.assertEquals(friendId, friend.getId());
        assertThat(user.getFriends(), hasItems(friend));
        verify(repository, times(1)).save(any(User.class));
        verify(repository, times(1)).findById(user.getId());
        verify(repository, times(1)).findById(friend.getId());
    }

    @Test
    @DisplayName("Обновление пользователя")
    public void updateTest() {
        User user = UserUtils.generateUser();
        UserEditDto editDto = userService.convertToEditDto(user);
        Mockito.when(repository.findById(any())).thenReturn(Optional.of(user));
        Mockito.when(repository.save(any(User.class))).thenReturn(user);
        UUID updatedId = userService.update(user.getId(), editDto);

        Assertions.assertEquals(updatedId, user.getId());
        verify(repository, times(1)).findById(user.getId());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void deleteTest() {
        UUID id = UUID.randomUUID();
        Mockito.when(repository.existsById(id)).thenReturn(true);

        Assertions.assertEquals(userService.delete(id), id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void deleteFriend() {
        User user = UserUtils.generateUser();
        User friend = UserUtils.generateUser();
        user.getFriends().add(friend);
        Mockito.when(repository.existsById(friend.getId())).thenReturn(true);
        Mockito.when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(repository.findById(friend.getId())).thenReturn(Optional.of(friend));
        Mockito.when(repository.save(any(User.class))).thenReturn(user);

        Assertions.assertEquals(userService.deleteFriend(user.getId(), friend.getId()), friend.getId());
        verify(repository, times(1)).save(any(User.class));
        verify(repository, times(1)).findById(user.getId());
        verify(repository, times(1)).findById(friend.getId());
    }

    @Test
    @DisplayName("Преобразование EditDto в сущность пользователя")
    public void convertEditDtoToUserTest() {
        UserEditDto editDto = UserUtils.generateUserEditDto();
        User user = new User();
        user = userService.convertEditDtoToUser(editDto, user);

        UserUtils.assertEquals(user, editDto);
    }

    @Test
    @DisplayName("Преобразование пользователя в EditDto")
    public void convertToEditDtoTest() {
        User user = UserUtils.generateUser();
        UserEditDto editDto = userService.convertToEditDto(user);

        UserUtils.assertEquals(user, editDto);
    }

    @Test
    @DisplayName("Преобразование пользователя в ListDto")
    public void convertToListDtoTest() {
        User user = UserUtils.generateUser();
        UserListDto listDto = userService.convertToListDto(user);

        UserUtils.assertEquals(user, listDto);
    }

}
