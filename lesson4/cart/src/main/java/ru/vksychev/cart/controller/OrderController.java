package ru.vksychev.cart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.vksychev.cart.domain.Order;
import ru.vksychev.cart.service.OrderService;


/**
 *  REST контроллер для обработки заказов
 */
@RestController
public class OrderController {


    final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Обработка post запроса по созданию заказа
     *
     * @param order данные о заказе
     * @return Json с ответом о созданном заказе
     */
    @RequestMapping(value = "/api/v1/order", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order currentOrder = orderService.createOrder(order);
        if(currentOrder == null){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(currentOrder);
    }
}
