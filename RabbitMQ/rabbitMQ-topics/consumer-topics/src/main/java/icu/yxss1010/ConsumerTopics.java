package icu.yxss1010;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerTopics {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1、创建连接工场
        ConnectionFactory factory = new ConnectionFactory();
        //2、配置参数
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        //3、创建连接
        Connection connection = factory.newConnection();
        //4、创建管道
        Channel channel = connection.createChannel();
        //5、接收消息
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("order=>" + new String(body));
            }
        };
        channel.basicConsume("order", true, consumer);
    }
}
