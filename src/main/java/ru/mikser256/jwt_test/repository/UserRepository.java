package ru.mikser256.jwt_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mikser256.jwt_test.model.User;

/**
 * репозиторий пользователей
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
