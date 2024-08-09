package com.example.HRM_Portal.consumer;

import com.example.HRM_Portal.entity.Status;
import com.example.HRM_Portal.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class RabbitMQJsonConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consumeJsonMessage(Status user){
        if(user != null){
            LOGGER.info(String.format("Received JSON message -> %s", user.toString()));
        }else{
            LOGGER.info(("Data not Received!"));
        }

    }
}