package ru.mikser256.jwt_test.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * класс dto сообщения
 */
@Builder
@Getter
@Schema(description = "Сущность сообщения")
public class MessageDto {
    @Schema(description = "Текст сообщения", example = "текст сообщения")
    private String text;
    @Schema(description = "Дата создания сообщения", example = "2022-11-30 12:05:33")
    private String created;
    @Schema(description = "Автр сообщения", example = "Автор")
    private String author;
}
