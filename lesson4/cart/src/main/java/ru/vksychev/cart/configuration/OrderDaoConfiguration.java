package ru.vksychev.cart.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ru.vksychev.cart.dao.OrderDao;

@Configuration
@RequiredArgsConstructor
public class OrderDaoConfiguration {

    private final JdbcTemplate jdbcTemplate;

    @Bean
    public OrderDao orderDao(){
        return new OrderDao(jdbcTemplate, new GeneratedKeyHolder());
    }
}