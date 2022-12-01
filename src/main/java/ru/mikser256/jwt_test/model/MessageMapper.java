package ru.mikser256.jwt_test.model;

import ru.mikser256.jwt_test.model.dto.MessageDto;

import java.time.format.DateTimeFormatter;

/**
 * маппер сообщений
 */
public class MessageMapper {
    public static MessageDto toMessageDto(Message message) {
        return MessageDto.builder()
                .author(message.getAuthor().getName())
                .text(message.getText())
                .created(message.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
