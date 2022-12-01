package ru.mikser256.jwt_test.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * класс dto токена авторизации
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность токена")
public class TokenDto {
    @Schema(description = "токен", example = "eyJhbGciOiJub25lIn0.eyJzdWIiOiJ0ZXN0In0.")
    private String token;

    @Override
    public String toString() {
        return "TokenDto{" +
                "token='" + token + '\'' +
                '}';
    }

}
