package ru.vksychev.cart.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vksychev.cart.dao.OrderDao;
import ru.vksychev.cart.dao.OrderDaoJdbc;

@Configuration
public class OrderDaoConfiguration {

    @Bean
    public OrderDao orderDao(){
        return new OrderDaoJdbc();
    }
}
