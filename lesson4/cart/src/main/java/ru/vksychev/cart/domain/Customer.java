package ru.vksychev.cart.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * Класс покупателя
 */
@Setter
@Getter
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
    @NonNull
    private String name;

    /**
     * Email покупателя
     */
    @NonNull
    private String email;
}
