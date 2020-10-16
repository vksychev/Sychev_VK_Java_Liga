package ru.vksychev.cart.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.vksychev.cart.domain.Order;

import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * DAO для работы с заказами
 */
public class OrderDaoJdbc implements OrderDao {

    /**
     * JdbcTemplate для работы с БД
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Запрос на создание заказа в БД
     */
    String INSERT_ORDER_SQL = "insert into ORDERS (name, price, customer_id) values(?,?,?)";

    /**
     * Создание заказа в БД
     *
     * @param order заказ для внедрения в БД
     * @return созданный заказ
     */
    public Order createOrder(Order order) {

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int affectedRows = jdbcTemplate.update(connection -> {
                        PreparedStatement ps = connection.prepareStatement(INSERT_ORDER_SQL, Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, order.getName());
                        ps.setInt(2, order.getPrice());
                        ps.setInt(3, order.getCustomerId());
                        return ps;
                    }, keyHolder
            );

            if (affectedRows <= 0) {
                return null;
            }else {
                order.setId(keyHolder.getKey().intValue());
                return order;
            }
        } catch (DataIntegrityViolationException e){

            return null;
        }
    }
}
