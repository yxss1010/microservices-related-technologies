package icu.yxss1010.springboot;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = "simplest_queue")
    public void listenerQueue(Message message){
        System.out.println(new String(message.getBody()));
    }
}
