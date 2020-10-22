package ru.vksychev.cart.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;


/**
 * Класс заказа
 */
@Setter
@Getter
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
    @NonNull
    private String name;

    /**
     * Цена заказа
     */
    @NonNull
    private Integer price;

    /**
     * ID покупателя
     */
    @NonNull
    private Customer customer;
}
