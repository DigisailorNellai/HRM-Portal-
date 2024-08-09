package com.example.HRM_Portal.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.HRM_Portal.entity.Entity;
import com.example.HRM_Portal.entity.Status;
import com.example.HRM_Portal.publisher.RabbitMQJsonProducer;
import com.example.HRM_Portal.service.MessageProcessingService;

@Service
public class Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private RabbitMQJsonProducer rabbitMQJsonProducer;

    @Autowired
    private MessageProcessingService messageProcessingService;

    // Listen to the queue defined in application.properties with the key "rabbitmq.queue.json.name.response"
    @RabbitListener(queues = {"${rabbitmq.queue.json.name.response}"})
    public void consumeJsonMessage(Entity entity) {

        messageProcessingService.processMessage(entity);
        // Check if the received message is not null
        if(entity != null){
            LOGGER.info(String.format("Received JSON Message -> %s", entity.toString()));
            // Send response back
            senderResponse();
        }
    }

    // Method to send a response message
    public void senderResponse(){
        Status status = new Status();
        status.setResponse("ok");
        rabbitMQJsonProducer.sendJsonMessage(status);
    }
}
