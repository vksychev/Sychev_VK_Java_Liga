package ru.vksychev.cart.service;

import org.springframework.stereotype.Service;
import ru.vksychev.cart.dao.OrderDao;
import ru.vksychev.cart.domain.Order;


/**
 * Сервис для обработки заказа
 */
@Service
public class OrderService {
    /**
     *  DAO для заказа
     */
    final OrderDao orderDao;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }


    /**
     * Сохранение заказа в БД
     *
     * @param order заказ для внесения в БД
     * @return созданный заказ
     */
    public Order createOrder(Order order){
        return orderDao.createOrder(order);
    }
}
