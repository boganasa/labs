package ru.bogachenko;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.bogachenko.RabbitMQ.Message;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class Listener {
    private AtomicLong count = new AtomicLong(0L);

    @RabbitListener(queues = "${sample.rabbitmq.queue}")
    public void recievedMessage(Message message) {
        System.out.println("( "+count.incrementAndGet()+" ) Received = : " + message);

    }
}