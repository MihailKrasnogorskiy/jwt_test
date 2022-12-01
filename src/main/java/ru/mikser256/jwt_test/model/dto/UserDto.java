package ru.mikser256.jwt_test.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * класс dto пользователя
 */
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Сущность нового сообщения")
public class UserDto {
    @NotNull
    @NotBlank
    @Schema(description = "Имя пользователя", example = "Иван")
    private String name;
    @NotNull
    @NotBlank
    @Schema(description = "Пароль пользователя", example = "password")
    private String password;
}
