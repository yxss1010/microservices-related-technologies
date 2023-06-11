package icu.yxss1010.springboot;

import icu.yxss1010.springboot.rabbitmq.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class ApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    void testProducer() {
        rabbitTemplate.convertAndSend("", RabbitMQConfig.QUEUE_NAME,"simplest case");
    }
}
