package ru.vksychev.cart.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.vksychev.cart.dao.OrderDao;
import ru.vksychev.cart.dto.OrderDto;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class OrderServiceTest {

    /**
     * Mock объект OrderService
     */
    @Mock
    private OrderDao mockDao;

    /**
     * Инициализация mock объектов
     */
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Создание заказа - корректный вызов")
    public void createOrderTestOk() {
        OrderDto order = new OrderDto(null,"order", 10, 1);
        Mockito.when(mockDao.createOrder(order)).thenReturn(order);
        mockDao.createOrder(order);
        verify(mockDao, times(1)).createOrder(order);
    }
}
