package ru.vksychev.cart.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.vksychev.cart.domain.Order;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class OrderServiceTest {
    @Mock
    private OrderService mockService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Создание заказа - корректный вызов")
    public void createOrderTestOk(){
        Order order = new Order(null, "name", 11, 1);
        Mockito.when(mockService.createOrder(order)).thenReturn(order);

        Order currentOrder = mockService.createOrder(order);
        Assertions.assertEquals(currentOrder.getName(),order.getName());
        Assertions.assertEquals(currentOrder.getPrice(),order.getPrice());
        Assertions.assertEquals(currentOrder.getCustomerId(),order.getCustomerId());
        verify(mockService, times(1)).createOrder(order);
    }
}
