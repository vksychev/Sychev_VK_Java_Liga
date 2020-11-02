package ru.vksychev.cart.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Or;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.vksychev.cart.dto.OrderDto;

import javax.annotation.Resource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class OrderDaoJdbcTest {

    private OrderDao orderDao;

    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private GeneratedKeyHolder keyHolder;

    /**
     * Инициализация mock объектов
     */
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        orderDao = new OrderDao(jdbcTemplate, keyHolder);
    }

    @Test
    @DisplayName("Создание заказа - корректный вызов")
    public void createOrderOkTest() {
        OrderDto order = new OrderDto(null,"order", 10, 1);
        OrderDto resultOrder = new OrderDto(1,"order", 10, 1);

        Mockito.when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
                .thenReturn(1);

        Mockito.when(keyHolder.getKey())
                .thenReturn(1);

        Assertions.assertEquals(resultOrder,orderDao.createOrder(order));
        verify(jdbcTemplate,
                times(1)).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
        verify(keyHolder, times(1)).getKey();
    }

    @Test
    @DisplayName("Создание заказа - неудачная попытка создать заказ")
    public void createOrderFailTest() {
        OrderDto order = new OrderDto(null,"order", 10, 1);

        Mockito.when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
                .thenReturn(0);

        Mockito.when(keyHolder.getKey())
                .thenReturn(1);

        OrderDto resultOrder = orderDao.createOrder(order);

        Assertions.assertEquals(null , resultOrder.getId());
        Assertions.assertEquals(order , resultOrder);
        verify(jdbcTemplate,
                times(1)).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
        verify(keyHolder, times(0)).getKey();
    }

    @Test
    @DisplayName("Создание заказа - исключение при попытке создать заказ")
    public void createOrderThrowsExceptionTest() {
        OrderDto order = new OrderDto(null ,"order", 10, 1);
        Mockito.when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
                .thenThrow(DataIntegrityViolationException.class);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> orderDao.createOrder(order));

        verify(jdbcTemplate,
                times(1)).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
        verify(keyHolder, times(0)).getKey();
    }

}
