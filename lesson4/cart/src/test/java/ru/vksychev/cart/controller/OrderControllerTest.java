package ru.vksychev.cart.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.vksychev.cart.dto.OrderDto;
import ru.vksychev.cart.service.OrderService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class OrderControllerTest {


    OrderController orderController;
    /**
     * Mock объект OrderController
     */
    @Mock
    OrderService mockOrderService;

    /**
     * Инициализация mock объектов
     */
    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        orderController = new OrderController(mockOrderService);
    }

    @Test
    @DisplayName("Создание заказа - корректный вызов")
    public void createOrderOkTest(){
        OrderDto order = new OrderDto(null,"order", 10, 1);
        Mockito.when(mockOrderService.createOrder(order)).thenReturn(order);
        orderController.createOrder(order);

        verify(mockOrderService, times(1)).createOrder(order);
    }
}
