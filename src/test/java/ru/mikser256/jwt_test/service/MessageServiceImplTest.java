package ru.mikser256.jwt_test.service;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.mikser256.jwt_test.exceptions.AuthorizationException;
import ru.mikser256.jwt_test.model.Message;
import ru.mikser256.jwt_test.model.MessageMapper;
import ru.mikser256.jwt_test.model.User;
import ru.mikser256.jwt_test.model.dto.MessageDto;
import ru.mikser256.jwt_test.model.dto.NewMessage;
import ru.mikser256.jwt_test.repository.MessageRepository;
import ru.mikser256.jwt_test.repository.TokenStorage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

class MessageServiceImplTest {
    MessageRepository mockMessageRepository = Mockito.mock(MessageRepository.class);

    TokenStorage tokenStorage = new TokenStorage();
    MessageService service = new MessageServiceImpl(mockMessageRepository, tokenStorage);
    User user = User.builder()
            .name("test")
            .password("test")
            .build();
    String token = Jwts.builder()
            .setSubject("test")
            .compact();
    NewMessage newMessage = new NewMessage("test", "текст");
    NewMessage historyMessage = new NewMessage("test", "history 10");

    /**
     * создание сообщения
     */
    @Test
    void test4_create() {
        tokenStorage.save(token, user);
        List<MessageDto> list = new ArrayList<>();
        Message message = Message.builder()
                .created(LocalDateTime.now())
                .author(user)
                .text("текст")
                .build();
        list.add(MessageMapper.toMessageDto(message));
        Mockito
                .when(mockMessageRepository.save(any()))
                .thenReturn(message);
        final List<MessageDto> result = service.create(token, newMessage);
        assertEquals(list.get(0).getCreated(), result.get(0).getCreated());
        assertEquals(list.get(0).getAuthor(), result.get(0).getAuthor());
        assertEquals(list.get(0).getText(), result.get(0).getText());
        NewMessage errorMessage = new NewMessage("error", "text");
        Throwable throwable = assertThrows(AuthorizationException.class, () -> service.create(token, errorMessage));
        assertEquals("Пара токен/имя пользователя не совпадает", throwable.getMessage());
    }

    /**
     * получение истории сообщений
     */
    @Test
    void test5_getHistory() {
        tokenStorage.save(token, user);
        List<Message> massageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message message = Message.builder()
                    .created(LocalDateTime.now())
                    .author(user)
                    .text("текст" + i)
                    .build();
            massageList.add(message);
        }
        List<MessageDto> dtoList = massageList.stream()
                .map(MessageMapper::toMessageDto).toList();
        Mockito
                .when(mockMessageRepository.findFirst10ByAuthorNameOrderByCreatedDesc(any()))
                .thenReturn(massageList);
        List<MessageDto> result = service.getHistory(token, historyMessage);
        assertEquals(10, result.size());
        assertEquals(dtoList.get(9).getCreated(), result.get(9).getCreated());
        assertEquals(dtoList.get(9).getAuthor(), result.get(9).getAuthor());
        assertEquals(dtoList.get(9).getText(), result.get(9).getText());
        NewMessage errorMessage = new NewMessage("error", "history 10");
        Throwable throwable = assertThrows(AuthorizationException.class, () -> service.create(token, errorMessage));
        assertEquals("Пара токен/имя пользователя не совпадает", throwable.getMessage());
    }

}