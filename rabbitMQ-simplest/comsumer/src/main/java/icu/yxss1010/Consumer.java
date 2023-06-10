package icu.yxss1010;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2.设置参数
        factory.setHost("10.1.19.110"); // ip 默认值： localhost
        factory.setPort(5672);// 端口 默认值：5672
        factory.setVirtualHost("/");// 虚拟机 默认值：/
        factory.setUsername("guest");// 用户名 默认值：guest
        factory.setPassword("guest");// 密码 默认值：guest
        // 3.创建连接 Connection
        Connection connection = factory.newConnection();
        // 4.创建 Channel
        Channel channel = connection.createChannel();
        // 5. 接受信息
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("body=>" + new String(body));
            }
        };
        /*
         * @Param
         *   1：queue：队列名称
         *   2：autoAck：是否自动确认
         *   3：callback：回调对象
         * */
        channel.basicConsume("test_queue", true, consumer);
    }
}
