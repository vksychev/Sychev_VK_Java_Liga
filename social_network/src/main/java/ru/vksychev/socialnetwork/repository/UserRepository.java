package ru.vksychev.socialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.vksychev.socialnetwork.domain.User;

import java.util.UUID;

/**
 * Репозиторий пользователя
 */
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
}
