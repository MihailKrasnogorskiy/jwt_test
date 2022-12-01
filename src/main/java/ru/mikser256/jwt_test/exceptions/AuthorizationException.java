package ru.mikser256.jwt_test.exceptions;

/**
 * исключение выбрасываемое при ошибке авторизации по токену
 */
public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {
        super("Пара токен/имя пользователя не совпадает");
    }
}
