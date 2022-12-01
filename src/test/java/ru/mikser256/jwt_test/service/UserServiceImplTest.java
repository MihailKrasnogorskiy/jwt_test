package ru.mikser256.jwt_test.service;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.mikser256.jwt_test.model.User;
import ru.mikser256.jwt_test.model.dto.TokenDto;
import ru.mikser256.jwt_test.model.dto.UserDto;
import ru.mikser256.jwt_test.repository.TokenStorage;
import ru.mikser256.jwt_test.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

class UserServiceImplTest {
    UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
    UserService service = new UserServiceImpl(mockUserRepository, new TokenStorage());

    /**
     * авторизация пользователя
     */
    @Test
    void test3_authorization() {
        UserDto userDto = new UserDto();
        userDto.setName("test");
        userDto.setPassword("test");

        TokenDto dto = new TokenDto(
                Jwts.builder()
                        .setSubject("test")
                        .compact());
        Mockito
                .when(mockUserRepository.findById(anyString()))
                .thenReturn(Optional.of(User.builder()
                        .name("test")
                        .password("test")
                        .build()));
        assertEquals(dto.getToken(), service.authorization(userDto).getToken());
    }
}