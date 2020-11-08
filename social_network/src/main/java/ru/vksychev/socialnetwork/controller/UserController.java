package ru.vksychev.socialnetwork.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vksychev.socialnetwork.dto.UserEditDto;
import ru.vksychev.socialnetwork.dto.UserListDto;
import ru.vksychev.socialnetwork.service.UserService;
import ru.vksychev.socialnetwork.service.filter.UserFilter;

import javax.validation.Valid;
import java.util.UUID;


/**
 * Контроллер для работы с пользователем
 */
@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Получить всех пользователей
     *
     * @return список DTO всех пользователей
     */
    @GetMapping
    public Page<UserListDto> findAll(UserFilter filter, Pageable pageable) {
        return userService.findAll(filter, pageable);
    }

    /**
     * Получить пользователя по id
     *
     * @param id идентификатор пользователя
     * @return DTO пользователя
     */
    @GetMapping("{id}")
    public UserEditDto findOne(@PathVariable UUID id) {
        return userService.findOne(id);
    }

    /**
     * Создание пользователя
     *
     * @param userEditDto форма с информацией о пользователе
     * @return response объект с соответствующим статусом состояния (200, 400, 409)
     */
    @PostMapping
    public UUID create(@RequestBody @Valid UserEditDto userEditDto) {
        return userService.create(userEditDto);
    }

    /**
     * Обновление данных пользователя
     *
     * @param id          идентификатор пользователя
     * @param userEditDto форма с информацией о пользователе
     * @return response объект с соответствующим статусом состояния (200, 400, 409)
     */
    @PutMapping("{id}")
    public UUID update(@PathVariable UUID id, @RequestBody @Valid UserEditDto userEditDto) {
        return userService.update(id, userEditDto);
    }

    /**
     * Удаление пользователя
     *
     * @param id идентификатор пользователя
     * @return response объект с соответствующим статусом состояния (200, 400, 409)
     */
    @DeleteMapping("{id}")
    public UUID delete(@PathVariable UUID id) {
        return userService.delete(id);
    }

    /**
     * Получить список друзей пользователя
     *
     * @param id идентификатор пользователя
     * @return список DTO друзей
     */
    @GetMapping("{id}/friends")
    public Page<UserListDto> findFriends(@PathVariable UUID id, Pageable pageable) {
        return userService.findFriends(id, pageable);
    }

    /**
     * Добавление пользователя в друзья
     *
     * @param id       идентификатор пользователя
     * @param targetId идентификатор друга
     * @return response объект с соответствующим статусом состояния (200, 400, 409)
     */
    @PostMapping("{id}/friends")
    public UUID addFriend(@PathVariable UUID id, @RequestParam UUID targetId) {
        return userService.addFriend(id, targetId);
    }

    /**
     * Удаление пользователя из друзья
     *
     * @param id       идентификатор пользователя
     * @param targetId идентификатор друга
     * @return id друга
     */
    @DeleteMapping("{id}/friends")
    public UUID deleteFriend(@PathVariable UUID id, @RequestParam UUID targetId) {
        return userService.deleteFriend(id, targetId);
    }
}
