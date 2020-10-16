package ru.vksychev.cart.dao;

import ru.vksychev.cart.domain.Order;


/**
 * Интерфейс DAO для работы с заказами
 */
public interface OrderDao {
    Order createOrder(Order order);
}
