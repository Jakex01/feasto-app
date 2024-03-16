package org.restaurant.config;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange("userExchange");
    }

}
