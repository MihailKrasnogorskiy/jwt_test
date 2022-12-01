package ru.mikser256.jwt_test.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.mikser256.jwt_test.exceptions.NotFoundException;
import ru.mikser256.jwt_test.model.User;

import java.util.HashMap;

/**
 * хранилище токенов авторизации
 */
@Component
@Slf4j
public class TokenStorage {
    private final HashMap<String, User> tokens = new HashMap<>();

    public boolean validateToken(String token) {
        return tokens.containsKey(token);
    }

    public User getUserByToken(String token) {
        if (validateToken(token)) {
            return tokens.get(token);
        } else {
            throw new NotFoundException(String.format("Данный токен %s не зарегистрирован", token));
        }
    }

    public void save(String token, User user) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("При сохранении токен не может быть пустым");
        }
        if (user == null) {
            throw new IllegalArgumentException("При сохранении необходимо передать действительный объект пользователя");
        }
        tokens.put(token, user);
        log.info("Токен {} сохранён", token);
    }
}
