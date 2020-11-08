package ru.vksychev.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserEditDto {

    /**
     * Идентификатор
     */
    UUID id;

    /**
     * Имя
     */
    @NotEmpty
    @Size(min = 1, max = 100)
    @Pattern(regexp = "\\w+")
    String firstName;

    /**
     * Фамилия
     */
    @NotEmpty
    @Size(min = 1, max = 100)
    @Pattern(regexp = "\\w+")
    String lastName;

    /**
     * День рождения
     */
    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-DD")
    LocalDate birthday;

    /**
     * Пол
     */
    @NotEmpty
    Integer sex;

    /**
     * Интересы
     */
    @NotEmpty
    @Size(max = 2500)
    String interests;

    /**
     * Город
     */
    @NotEmpty
    @Size(min = 1, max = 100)
    String city;


}
