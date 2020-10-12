package ru.vksychev.cart.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


/**
 * Класс заказа
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    /**
     * ID заказа
     */
    @Id
    private Integer id;

    /**
     * Название заказа
     */
    private String name;

    /**
     * Цена заказа
     */
    private Integer price;

    /**
     * ID покупателя
     */
    private Integer customerId;
}
