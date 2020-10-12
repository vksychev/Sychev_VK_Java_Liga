package ru.vksychev.cart.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * Класс покупателя
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    /**
     * ID покупателя
     */
    @Id
    private Integer id;

    /**
     * Имя покупателя
     */
    private String name;

    /**
     * Email покупателя
     */
    private String email;
}
