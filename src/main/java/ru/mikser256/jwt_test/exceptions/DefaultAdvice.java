package ru.mikser256.jwt_test.exceptions;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * контроллер обработки исключений
 */
@RestControllerAdvice
@Slf4j
public class DefaultAdvice {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "Запрашиваемый объект не найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))
                    })
    })
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleException(NotFoundException e) {
        log.error(e.getMessage());
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setReason("Запрашиваемый объект не найден");
        apiError.setStatus("NOT_FOUND");
        Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .forEach(apiError.getErrors()::add);
        apiError.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "401",
                    description = "Ошибка авторизации",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))
                    })
    })
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiError> handleException(AuthorizationException e) {
        log.error(e.getMessage());
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setReason("Ошибка авторизации");
        apiError.setStatus("UNAUTHORIZED");
        Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .forEach(apiError.getErrors()::add);
        apiError.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }
}
