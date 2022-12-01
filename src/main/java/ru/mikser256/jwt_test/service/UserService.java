package ru.mikser256.jwt_test.service;

import ru.mikser256.jwt_test.model.dto.TokenDto;
import ru.mikser256.jwt_test.model.dto.UserDto;

/**
 * интерфейс сервиса пользователей
 */
public interface UserService {
    /**
     * авторизация пользователя
     *
     * @param userDto - объект авторизующегося пользователя
     * @return - dto объект токена
     */
    TokenDto authorization(UserDto userDto);
}
