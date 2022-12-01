package ru.mikser256.jwt_test.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * класс нового сообщения полученного для сохранения
 */
@Getter
@AllArgsConstructor
@Schema(description = "Сущность нового сообщения")
public class NewMessage {
    @NotNull
    @NotBlank
    @Schema(description = "Имя пользователя", example = "Автор")
    private String name;
    @NotNull
    @NotBlank
    @Schema(description = "Текст сообщения, если текст сообщения \"history 10\", то в ответ будет возвращены 10" +
            " последних сообщений пользователя", example = "текст сообщения")
    private String message;
}
