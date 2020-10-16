package ru.vksychev.cart.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.vksychev.cart.domain.Order;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class OrderControllerTest {

    /**
     * Mock объект OrderController
     */
    @Mock
    OrderController orderController;

    /**
     * Инициализация mock объектов
     */
    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Создание заказа - корректный вызов")
    public void createOrderOkTest(){
        Order order = new Order(null, "name", 11, 1);
        Mockito.when(orderController.createOrder(order)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(order));

        Assertions.assertEquals(orderController.createOrder(order),ResponseEntity.status(HttpStatus.OK).body(order));
        verify(orderController, times(1)).createOrder(order);
    }
}
