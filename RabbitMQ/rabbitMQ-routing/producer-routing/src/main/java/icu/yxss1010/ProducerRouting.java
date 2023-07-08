package icu.yxss1010;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerRouting {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1、创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2、设置参数
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        //3、创建连接
        Connection connection = factory.newConnection();
        //4、创建管道
        Channel channel = connection.createChannel();
        //5、定义路由器
        String exchangeName = "routing_exchange";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true, false, false, null);
        //6、定义队列
        String infoQueue = "info_queue";
        String errorQueue = "error_queue";
        channel.queueDeclare(infoQueue, true, false, false, null);
        channel.queueDeclare(errorQueue, true, false, false ,null);
        //7、绑定队列和交换机
        channel.queueBind(infoQueue, exchangeName, "info");
        channel.queueBind(infoQueue, exchangeName, "error");
        channel.queueBind(errorQueue, exchangeName, "error");
        //8、发送消息
        String body = "出现未知错误！";
        channel.basicPublish(exchangeName, "error", null, body.getBytes());
        //9、释放资源
        channel.close();
        connection.close();
    }
}