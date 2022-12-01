package ru.mikser256.jwt_test.repository;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import ru.mikser256.jwt_test.exceptions.NotFoundException;
import ru.mikser256.jwt_test.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenStorageTest {
    User user = User.builder()
            .name("test")
            .password("test")
            .build();
    String token = Jwts.builder()
            .setSubject("test")
            .compact();
    TokenStorage storage = new TokenStorage();

    /**
     * получение пользователя по токену
     */
    @Test
    void getUserByToken() {
        storage.save(token, user);
        assertEquals(user, storage.getUserByToken(token));
        String errorToken = "error";
        Throwable throwable = assertThrows(NotFoundException.class, () -> storage.getUserByToken(errorToken));
        assertEquals("Данный токен error не зарегистрирован", throwable.getMessage());
    }

    /**
     * сохранение
     */
    @Test
    void save() {
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> storage.save(token, null));
        assertEquals("При сохранении необходимо передать действительный объект пользователя",
                throwable.getMessage());
        throwable = assertThrows(IllegalArgumentException.class, () -> storage.save("", user));
        assertEquals("При сохранении токен не может быть пустым", throwable.getMessage());
    }
}