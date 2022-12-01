package ru.mikser256.jwt_test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mikser256.jwt_test.model.dto.TokenDto;
import ru.mikser256.jwt_test.model.dto.UserDto;
import ru.mikser256.jwt_test.service.UserService;

import javax.validation.Valid;

/**
 * контроллер пользователя
 */
@RestController
@RequestMapping("/users")
@Tag(name="Контроллер авторизации пользователей", description="Контроллер предназначенный для авторизации пользователей" +
        " и получения токена.")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * авторизация пользователя
     *
     * @param userDto - объект авторизующегося пользователя
     * @return - dto объект токена
     */
    @Operation(
            summary = "Авторизация пользователя",
            description = "Позволяет авторизовать пользователя"
    )
    @PostMapping
    public TokenDto authorization(@Valid @RequestBody @Parameter(description =
            "сущность пользователя") UserDto userDto) {
        return service.authorization(userDto);
    }
}
