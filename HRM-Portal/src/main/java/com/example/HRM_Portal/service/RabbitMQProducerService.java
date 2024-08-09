package com.example.HRM_Portal.service;

import com.example.HRM_Portal.entity.Entity;
import com.example.HRM_Portal.entity.OurUsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class RabbitMQProducerService {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducerService.class);

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(Entity user) {
        if (user == null) {
            LOGGER.error("User object is null. Cannot send message.");
            return;
        }
        LOGGER.info("Json message sent -> {}", user);
        rabbitTemplate.convertAndSend(exchange, routingJsonKey, user);
    }
}