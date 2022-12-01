package ru.mikser256.jwt_test.service;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mikser256.jwt_test.exceptions.NotFoundException;
import ru.mikser256.jwt_test.model.User;
import ru.mikser256.jwt_test.model.dto.TokenDto;
import ru.mikser256.jwt_test.model.dto.UserDto;
import ru.mikser256.jwt_test.repository.TokenStorage;
import ru.mikser256.jwt_test.repository.UserRepository;

/**
 * сервис пользователей
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenStorage tokenStorage;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, TokenStorage tokenStorage) {
        this.userRepository = userRepository;
        this.tokenStorage = tokenStorage;
    }

    @Override
    public TokenDto authorization(UserDto userDto) {
        User user = userRepository.findById(userDto.getName()).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с именем %s не найден", userDto.getName())));
        if (userDto.getPassword().equals(user.getPassword())) {
            String tokenStr = Jwts.builder()
                    .setSubject(user.getName())
                    .compact();
            tokenStorage.save(tokenStr, user);
            log.info("Токен {} создан", tokenStr);
            return new TokenDto(tokenStr);
        }
        return null;
    }
}
