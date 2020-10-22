package ru.vksychev.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vksychev.cart.dto.OrderDto;
import ru.vksychev.cart.service.OrderService;


/**
 * REST контроллер для обработки заказов
 */
@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    /**
     * Обработка post запроса по созданию заказа
     * При неудачном формировании заказа возвращает null
     *
     * @param orderDto данные о заказе
     * @return Json с ответом о созданном заказе
     */
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        OrderDto currentOrder = orderService.createOrder(orderDto);
        return ResponseEntity.status(HttpStatus.OK).body(currentOrder);
    }

}
