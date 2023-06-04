package ru.bogachenko;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.bogachenko.OOPModel.CatOOP;
import ru.bogachenko.services.CatImpl;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class Listener {
    private AtomicLong count = new AtomicLong(0L);
    private ObjectMapper objectMapper;

    CatImpl catImpl;

    @RabbitListener(queues = "${sample.rabbitmq.queue}")
    public void recievedMessage(String catOOP) throws JsonProcessingException {
        CatOOP cat = objectMapper.readValue(catOOP, CatOOP.class);

        if (!Objects.isNull(cat.getId())){
            catImpl.getCatById(cat.getId());
        } else {
            catImpl.createNewCat(cat);
        }

    }
}