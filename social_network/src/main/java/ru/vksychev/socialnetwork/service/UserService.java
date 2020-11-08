package ru.vksychev.socialnetwork.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.vksychev.socialnetwork.domain.User;
import ru.vksychev.socialnetwork.dto.UserEditDto;
import ru.vksychev.socialnetwork.dto.UserListDto;
import ru.vksychev.socialnetwork.exception.UserNotFoundException;
import ru.vksychev.socialnetwork.repository.UserRepository;
import ru.vksychev.socialnetwork.service.filter.UserFilter;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Сервис для работы с пользователем
 */
@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Получение всех пользователей
     *
     * @return список из dto {@link UserEditDto} всех пользователей
     */
    public Page<UserListDto> findAll(UserFilter filter, Pageable pageable) {
        return userRepository.findAll(filter.toSpecification(), pageable)
                .map(this::convertToListDto);
    }

    /**
     * Получение пользователя по id
     *
     * @param id идентификатор пользователя
     * @return dto {@link UserEditDto} пользователя
     */
    public UserEditDto findOne(UUID id) {
        return userRepository.findById(id)
                .map(this::convertToEditDto)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    /**
     * Получение друзей пользователя
     *
     * @param id идентификатор пользователя
     * @return список из dto {@link UserListDto} всех друзей
     */
    public Page<UserListDto> findFriends(UUID id, Pageable pageable) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<User> users = user.getFriends();
        return new PageImpl<>(users
                .stream()
                .map(this::convertToListDto)
                .collect(Collectors.toList()), pageable, users.size());
    }

    /**
     * Создание пользователя
     *
     * @param userEditDto форма с информацией о пользователе
     * @return id созданного пользователя
     */
    @Transactional
    public UUID create(UserEditDto userEditDto) {
        User user = convertEditDtoToUser(userEditDto, new User());
        user = userRepository.save(user);
        return user.getId();
    }

    /**
     * Добавление пользователя в друзья
     *
     * @param id       идентификатор пользователя
     * @param friendId идентификатор друга
     * @return id друга
     */
    @Transactional
    public UUID addFriend(UUID id, UUID friendId) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        User friend = userRepository.findById(friendId).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.getFriends().add(friend);
        userRepository.save(user);
        return friendId;
    }

    /**
     * Обновление пользователя
     *
     * @param id          идентификатор пользователя
     * @param userEditDto форма с информацией о пользователе
     * @return id обновленного пользователя
     */
    @Transactional
    public UUID update(UUID id, UserEditDto userEditDto) {
        User user = userRepository
                .findById(id)
                .map(usr -> convertEditDtoToUser(userEditDto, usr))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user = userRepository.save(user);
        return user.getId();
    }

    /**
     * Удаление пользователя
     *
     * @param id идентификатор пользователя
     * @return id удаленного пользователя
     */
    public UUID delete(UUID id) {
        if (id != null && userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
        return id;
    }

    /**
     * Удаление друга
     *
     * @param id       идентификатор пользователя
     * @param targetId идентификатор друга
     * @return id
     */
    @Transactional
    public UUID deleteFriend(UUID id, UUID targetId) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (targetId != null && userRepository.existsById(targetId)) {
            User friend = userRepository.findById(targetId).orElseThrow(() -> new UserNotFoundException("User not found"));
            user.getFriends().remove(friend);
            userRepository.save(user);
        }
        return targetId;
    }

    /**
     * Преобразование dto {@link UserEditDto} в сущность {@link User}
     *
     * @param user сущность пользователя
     * @param dto  форма с информацией о пользователе
     * @return сущность пользователя
     */
    User convertEditDtoToUser(UserEditDto dto, User user) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setBirthday(dto.getBirthday());
        user.setCity(dto.getCity());
        user.setSex(dto.getSex());
        user.setInterests(dto.getInterests());
        return user;
    }

    /**
     * Создание dto {@link UserEditDto} из сущности {@link User}
     *
     * @param user сущность пользователя
     * @return dto пользователя
     */
    UserEditDto convertToEditDto(User user) {
        UserEditDto dto = new UserEditDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setBirthday(user.getBirthday());
        dto.setCity(user.getCity());
        dto.setSex(user.getSex());
        dto.setInterests(user.getInterests());
        return dto;
    }

    /**
     * Создание dto {@link UserEditDto} из сущности {@link User}
     *
     * @param user сущность пользователя
     * @return dto пользователя
     */
    UserListDto convertToListDto(User user) {
        UserListDto dto = new UserListDto();
        dto.setId(user.getId());
        dto.setSex(user.getSex());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }


}
