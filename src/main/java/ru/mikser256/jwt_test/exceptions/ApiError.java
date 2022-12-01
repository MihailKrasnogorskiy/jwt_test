package ru.mikser256.jwt_test.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * класс ошибки
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сообщение об ошибке")
public class ApiError {
    @Schema(description = "stacktrace")
    private List<String> errors = new ArrayList<>();
    @Schema(description = "сообщение об ошибке", example = "Пользователь не найден")
    private String message;
    @Schema(description = "причина", example = "Иван")
    private String reason;
    @Schema(description = "http статус", example = "NOT_FOUND")
    private String status;
    @Schema(description = "время")
    private String timestamp;

}