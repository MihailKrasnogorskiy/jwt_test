package ru.mikser256.jwt_test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.mikser256.jwt_test.model.dto.MessageDto;
import ru.mikser256.jwt_test.model.dto.NewMessage;
import ru.mikser256.jwt_test.service.MessageService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * тестовый класс контроллера запросов
 */

@WebMvcTest(controllers = MessageController.class)
class MessageControllerTest {
    @MockBean
    private MessageService service;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    /**
     * создание сообщений и получении истории сообщений
     */
    @Test
    void test1_create() throws Exception {
        NewMessage createMassage = new NewMessage("test", "test_text");
        Mockito
                .when(service.create(anyString(), any()))
                .thenReturn(new ArrayList<>());

        mvc.perform(post("/messages")
                        .content(mapper.writeValueAsString(createMassage))
                        .header("Authorization", "Bearer_1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));


        List<MessageDto> list = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            MessageDto dto1 = MessageDto.builder()
                    .author("test")
                    .created(String.valueOf(i))
                    .text("text")
                    .build();
            list.add(dto1);
        }

        Mockito
                .when(service.getHistory(anyString(), any()))
                .thenReturn(list);
        NewMessage historyMassage = new NewMessage("test", "history 10");
        mvc.perform(post("/messages")
                        .content(mapper.writeValueAsString(historyMassage))
                        .header("Authorization", "Bearer_1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[9].created", is(list.get(9).getCreated()), String.class));
        mvc.perform(post("/messages")
                        .content(mapper.writeValueAsString(historyMassage))
                        .header("Authorization", "BAarer_1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        mvc.perform(post("/messages")
                        .content(mapper.writeValueAsString(historyMassage))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mvc.perform(post("/messages")
                        .content(mapper.writeValueAsString(historyMassage))
                        .header("Authorization", "null")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

}