package ru.mikser256.jwt_test.service;

import ru.mikser256.jwt_test.model.dto.MessageDto;
import ru.mikser256.jwt_test.model.dto.NewMessage;

import java.util.List;

/**
 * интерфейс сервиса сообщений
 */
public interface MessageService {
    /**
     * создание сообщения
     *
     * @param bearer     - токен автоирзации
     * @param newMessage - объект нового сообщения
     * @return - список сообщений пользователя
     */
    List<MessageDto> create(String bearer, NewMessage newMessage);

    /**
     * получение истории сообщений
     *
     * @param bearer     - токен автоирзации
     * @param newMessage - объект нового сообщения
     * @return - список сообщений пользователя
     */
    List<MessageDto> getHistory(String bearer, NewMessage newMessage);
}
