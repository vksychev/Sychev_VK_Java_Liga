package ru.vksychev.cart.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class OrderDto {
    Integer id;
    String name;
    Integer price;
    Integer customerId;
}
