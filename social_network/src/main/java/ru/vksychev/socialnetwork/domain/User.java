package ru.vksychev.socialnetwork.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "usr")
@ToString
public class User {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @NotEmpty
    private UUID id;

    /**
     * Имя
     */
    @Column(name = "first_name", nullable = false)
    @NotEmpty
    private String firstName;

    /**
     * Фамилия
     */
    @Column(name = "last_name", nullable = false)
    @NotEmpty
    private String lastName;

    /**
     * День рождения
     */
    @Column(name = "birthday", nullable = false)
    @NotEmpty
    private LocalDate birthday;

    /**
     * Пол
     * 0-Not known, 1-Male, 2-Female, 9-Not applicable
     */
    @Column(name = "sex", nullable = false)
    @NotEmpty
    private Integer sex;

    /**
     * Интересы
     */
    @Column(name = "interests")
    @NotEmpty
    private String interests;

    /**
     * Город
     */
    @Column(name = "city", nullable = false)
    @NotEmpty
    private String city;

    /**
     * Друзья
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usr_relationship",
            joinColumns = @JoinColumn(name = "relating_id"),
            inverseJoinColumns = @JoinColumn(name = "related_id")
    )
    private List<User> friends;


}
