package ru.mikser256.jwt_test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.stream.Collectors;

/**
 * сервис сообщений
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final TokenStorage tokenStorage;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, TokenStorage tokenStorage) {
        this.messageRepository = messageRepository;
        this.tokenStorage = tokenStorage;
    }

    @Override
    @Transactional
    public List<MessageDto> create(String bearer, NewMessage newMessage) {
        validateAuthorization(bearer, newMessage);
        User user = tokenStorage.getUserByToken(bearer);
        Message message = Message.builder()
                .created(LocalDateTime.now())
                .author(user)
                .text(newMessage.getMessage())
                .build();
        message = messageRepository.save(message);
        log.info("Сообщение {} сохранено", message);
        List<MessageDto> list = new ArrayList<>();
        list.add(MessageMapper.toMessageDto(message));
        return list;
    }

    @Override
    public List<MessageDto> getHistory(String bearer, NewMessage newMessage) {
        validateAuthorization(bearer, newMessage);

        return messageRepository.findFirst10ByAuthorNameOrderByCreatedDesc(newMessage.getName()).stream()
                .map(MessageMapper::toMessageDto)
                .collect(Collectors.toList());
    }

    private void validateAuthorization(String bearer, NewMessage newMessage) {
        if (!tokenStorage.getUserByToken(bearer).getName().equals(newMessage.getName())) {
            throw new AuthorizationException();
        }
    }
}
