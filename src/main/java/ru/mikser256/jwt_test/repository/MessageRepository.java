package ru.mikser256.jwt_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mikser256.jwt_test.model.Message;

import java.util.List;

/**
 * репозиторий пользователя
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findFirst10ByAuthorNameOrderByCreatedDesc(String name);
}
