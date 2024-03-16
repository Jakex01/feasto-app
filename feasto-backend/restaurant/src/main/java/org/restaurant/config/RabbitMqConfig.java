package org.restaurant.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    public Queue userQueue() {
        return new Queue("userQueue", false);
    }

    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange("userExchange");
    }

    @Bean
    public Binding binding(Queue userQueue, TopicExchange userExchange) {
        return BindingBuilder.bind(userQueue).to(userExchange).with("user.#");
    }

}
