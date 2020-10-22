package ru.vksychev.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vksychev.cart.dao.OrderDao;
import ru.vksychev.cart.dto.OrderDto;


/**
 * Сервис для обработки заказа
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDao orderDao;

    /**
     * Создание заказа в БД
     *
     * @param order заказ для внесения в БД
     * @return созданный заказ
     */
    public OrderDto createOrder(OrderDto order){
        return orderDao.createOrder(order);
    }
}
