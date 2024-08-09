package com.example.HRM_Portal.config;

import org.springframework.amqp.core.*;
        import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.json.name}")
    private String jsonQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    //response config
    @Value("${rabbitmq.queue.json.name.response}")
    private String responseQueue;

    @Value("${rabbitmq.exchange.name.response}")
    private String responseExchange;

    @Value("${rabbitmq.routing.json.key.response}")
    private String responseRoutingJsonKey;


    @Bean
    public Queue jsonQueue() {
        return new Queue(jsonQueue);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue responseQueue() {
        return new Queue(responseQueue);
    }

    @Bean
    public TopicExchange responseExchange() {
        return new TopicExchange(responseExchange);
    }



    @Bean
    public Binding jsonBinding() {
        return BindingBuilder.bind(jsonQueue()).to(exchange()).with(routingJsonKey);
    }

    @Bean
    public Binding responseJsonBinding() {
        return BindingBuilder.bind(responseQueue()).to(responseExchange()).with(responseRoutingJsonKey);
    }


    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}