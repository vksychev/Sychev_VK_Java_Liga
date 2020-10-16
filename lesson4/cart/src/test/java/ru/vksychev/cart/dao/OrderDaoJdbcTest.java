package ru.vksychev.cart.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;
import ru.vksychev.cart.domain.Order;

import javax.annotation.Resource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class OrderDaoJdbcTest {

    /**
     * Mock объект OrderDaoJdbc
     */
    @Mock
    private OrderDaoJdbc orderDao;

    /**
     * Mock объект JdbcTemplate
     */
    @Mock
    private JdbcTemplate jdbcTemplate;

    /**
     * OrderDaoJdbc с внедренным mock jdbcTemplate
     */
    @InjectMocks
    @Resource
    private OrderDaoJdbc orderDaoTemplateMock;

    /**
     * Инициализация mock объектов
     */
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Создание заказа - корректный вызов")
    public void createOrderOkTest() {
        Order order = new Order(null, "name", 11, 1);
        Mockito.when(orderDao.createOrder(order))
                .thenReturn(order);
        Order currentOrder = orderDao.createOrder(order);

        Assertions.assertEquals(currentOrder.getName(), order.getName());
        Assertions.assertEquals(currentOrder.getPrice(), order.getPrice());
        Assertions.assertEquals(currentOrder.getCustomerId(), order.getCustomerId());
        verify(orderDao, times(1)).createOrder(order);
    }

    @Test
    @DisplayName("Создание заказа - не создалось без исключения")
    public void createOrderAffectedZeroTest() {
        Order order = new Order(null, "name", 11, 1);
        Mockito.when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
                .thenReturn(0);

        Assertions.assertNull(orderDaoTemplateMock.createOrder(order));
    }

    @Test
    @DisplayName("Создание заказа - исключение при попытке создать запись")
    public void createOrderThrowsExceptionTest() {
        Order order = new Order(null, "name", 11, 1);
        Mockito.when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
                .thenThrow(DataIntegrityViolationException.class);

        Assertions.assertNull(orderDaoTemplateMock.createOrder(order));
    }

}
