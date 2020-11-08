package ru.vksychev.socialnetwork.service.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import ru.vksychev.socialnetwork.domain.User;
import ru.vksychev.socialnetwork.service.specification.BaseSpecification;

import static org.springframework.data.jpa.domain.Specification.where;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserFilter implements Filter<User> {

    /**
     * Имя пользователя
     */
    private String firstNameLike;

    /**
     * Фамилия
     */
    private String lastNameLike;

    /**
     * Пол пользователя
     */
    private Integer sex;


    @Override
    public Specification<User> toSpecification() {
        return where(BaseSpecification.<User>like("firstName", firstNameLike))
                .and(BaseSpecification.like("lastName", lastNameLike))
                .and(BaseSpecification.equal("sex", sex));
    }
}
