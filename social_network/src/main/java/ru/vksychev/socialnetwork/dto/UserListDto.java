package ru.vksychev.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Dto для представления сущности User{@ref User} в листе
 */
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserListDto {

    /**
     * Идентификатор
     */
    UUID id;

    /**
     * Имя
     */
    String firstName;

    /**
     * Фамилия
     */
    String lastName;

    /**
     * Пол
     */
    Integer sex;
}
