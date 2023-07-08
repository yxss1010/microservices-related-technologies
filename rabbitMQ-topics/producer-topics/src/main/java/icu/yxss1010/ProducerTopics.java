package icu.yxss1010;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerTopics {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1、创建连接工场
        ConnectionFactory factory = new ConnectionFactory();
        //2、设置参数
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        //3、创建连接
        Connection connection = factory.newConnection();
        //4、创建通道
        Channel channel = connection.createChannel();
        //5、定义交换机
        String topicExchange = "topic_exchange";
        channel.exchangeDeclare(topicExchange, BuiltinExchangeType.TOPIC, true, false, false, null);
        //6、定义队列
        String allError = "all.error";
        String orderInfo = "order";
        channel.queueDeclare(allError, true, false, false ,null);
        channel.queueDeclare(orderInfo, true, false, false ,null);
        //7、绑定队列与交换机
        channel.queueBind(allError, topicExchange, "*.error");
        channel.queueBind(orderInfo, topicExchange, "order.*");
        //8、发送消息
        String body = "***下单成功！";
        channel.basicPublish(topicExchange, "order.info", null, body.getBytes());
        //9、释放资源
        channel.close();
        connection.close();
    }
}