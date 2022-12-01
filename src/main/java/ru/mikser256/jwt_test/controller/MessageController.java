package ru.mikser256.jwt_test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.mikser256.jwt_test.exceptions.AuthorizationException;
import ru.mikser256.jwt_test.model.dto.MessageDto;
import ru.mikser256.jwt_test.model.dto.NewMessage;
import ru.mikser256.jwt_test.service.MessageService;

import javax.validation.Valid;
import java.util.List;

/**
 * контроллер сообщений
 */
@RestController
@RequestMapping("/messages")
@Tag(name="Контроллер сообщений", description="Контроллер предназначенный для работы с сообщениями. " +
        "Для работы с эндпоинтами требуется авторизация по токену.")
public class MessageController {
    private final MessageService messageService;
    private final String AUTHORIZATION = "Authorization";
    private final String HISTORY;

    @Autowired
    public MessageController(MessageService messageService, @Value("${history.size}") String history) {
        this.messageService = messageService;
        HISTORY = history;
    }

    /**
     * создание сообщения
     *
     * @param bearer  - http заголовок авторизации
     * @param massage - объект нового сообщения
     * @return - список сообщений пользователя
     */
    @Operation(
            summary = "Работа с сообщениями пользователя",
            description = "Позволяет сохранить сообщение или получить историю сообщении пользователя"
    )
    @PostMapping()
    public List<MessageDto> create(@RequestHeader(AUTHORIZATION) @Parameter(description = "Токен полученный при авторизации" +
            " перед токеном необходимо добавить \"Bearer_\"")String bearer, @Valid @RequestBody @Parameter(description =
            "сущность нового сообщения") NewMessage massage) {
        String token = getTokenFromHeader(bearer);
        if (token != null) {
            if (massage.getMessage().equals(HISTORY)) {
                return messageService.getHistory(token, massage);
            }
            return messageService.create(token, massage);

        } else {
            throw new AuthorizationException();
        }
    }

    /**
     * получение токена из http заголовка
     *
     * @param bearer - http заголовок
     * @return - токен
     */
    private String getTokenFromHeader(final String bearer) {
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer_")) {
            return bearer.substring(7);
        }
        return null;
    }
}
