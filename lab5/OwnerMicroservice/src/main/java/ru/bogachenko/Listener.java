package ru.bogachenko;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.bogachenko.OOPModel.OwnerOOP;
import ru.bogachenko.services.OwnerImpl;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class Listener {
    private AtomicLong count = new AtomicLong(0L);
    private ObjectMapper objectMapper;

    OwnerImpl ownerImpl;

    @RabbitListener(queues = "${sample.rabbitmq.queue}")
    public void recievedMessage(String ownerOOP) throws JsonProcessingException {
        OwnerOOP owner = objectMapper.readValue(ownerOOP, OwnerOOP.class);

        if (!Objects.isNull(owner.getId())){
            ownerImpl.getOwnerById(owner.getId());
        } else {
            ownerImpl.createOwner(owner);
        }

    }
}