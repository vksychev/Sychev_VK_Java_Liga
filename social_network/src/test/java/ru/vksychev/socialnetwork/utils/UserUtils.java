package ru.vksychev.socialnetwork.utils;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Assertions;
import ru.vksychev.socialnetwork.domain.User;
import ru.vksychev.socialnetwork.dto.UserEditDto;
import ru.vksychev.socialnetwork.dto.UserListDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class UserUtils {

    public static void assertEquals(User user, UserEditDto editDto) {
        Assertions.assertEquals(editDto.getId(), user.getId());
        Assertions.assertEquals(editDto.getFirstName(), user.getFirstName());
        Assertions.assertEquals(editDto.getLastName(), user.getLastName());
        Assertions.assertEquals(editDto.getBirthday(), user.getBirthday());
        Assertions.assertEquals(editDto.getSex(), user.getSex());
        Assertions.assertEquals(editDto.getInterests(), user.getInterests());
    }

    public static void assertEquals(User user, UserListDto listDto) {
        Assertions.assertEquals(listDto.getId(), user.getId());
        Assertions.assertEquals(listDto.getFirstName(), user.getFirstName());
        Assertions.assertEquals(listDto.getLastName(), user.getLastName());
        Assertions.assertEquals(listDto.getSex(), user.getSex());
    }

    public static User generateUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName(RandomString.make(15).replaceAll("[0-9]", "a"));
        user.setLastName(RandomString.make(15).replaceAll("[0-9]", "a"));
        user.setBirthday(LocalDate.of(2018, 1, 1));
        user.setSex(1);
        user.setCity(RandomString.make(15).replaceAll("[0-9]", "a"));
        user.setFriends(new ArrayList<>());
        user.setInterests(RandomString.make(15).replaceAll("[0-9]", "a"));
        return user;
    }

    public static UserEditDto generateUserEditDto() {
        UserEditDto editDto = new UserEditDto();
        editDto.setFirstName(RandomString.make(15).replaceAll("[0-9]", "a"));
        editDto.setLastName(RandomString.make(15).replaceAll("[0-9]", "a"));
        editDto.setBirthday(LocalDate.of(2018, 1, 1));
        editDto.setSex(1);
        editDto.setCity(RandomString.make(15).replaceAll("[0-9]", "a"));
        editDto.setInterests(RandomString.make(15).replaceAll("[0-9]", "a"));
        return editDto;
    }
}
