package ru.vksychev.cart.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import ru.vksychev.cart.dto.OrderDto;

import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * DAO для работы с заказами
 */
@RequiredArgsConstructor
public class OrderDao {

    /**
     * Запрос на создание заказа в БД
     */
    final String INSERT_ORDER_SQL = "insert into ORDERS (name, price, customer_id) values(?,?,?)";

    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder;

    /**
     * Создание заказа в БД
     *
     * @param orderDto заказ для внедрения в БД
     * @return созданный заказ
     */
    public OrderDto createOrder(OrderDto orderDto) {
        int affectedRows = jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_ORDER_SQL, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, orderDto.getName());
                    ps.setInt(2, orderDto.getPrice());
                    ps.setInt(3, orderDto.getCustomerId());
                    return ps;
                }, keyHolder
        );

        if (affectedRows > 0) {
            orderDto.setId(keyHolder.getKey().intValue());
        }
        return orderDto;
    }
}

