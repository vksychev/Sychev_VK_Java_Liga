package ru.vksychev.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.vksychev.cart.dto.OrderDto;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Sql(scripts = "beforeTest.sql")
    @DisplayName("Создание заказа в БД - корректный вызов")
    void insertOrderIntoDBOk() throws Exception {
        String jsonContent = "{\"name\":\"order\",\"price\":10, \"customerId\": 1}";
        mockMvc.perform(post("/api/v1/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"order\",\"price\":10, \"customerId\": 1}"));
    }

    @Test
    @Sql(scripts = "beforeTest.sql")
    @DisplayName("Создание заказа в БД - неправильный customerId")
    void insertOrderIntoDBWrongUserId() throws Exception {
        String jsonContent = "{\"name\":\"order\",\"price\":10, \"customerId\": 10}";
        mockMvc.perform(post("/api/v1/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isConflict());
    }




 }
