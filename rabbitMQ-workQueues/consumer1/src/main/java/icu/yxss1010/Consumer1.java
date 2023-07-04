package icu.yxss1010;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1、 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2、 设置参数
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        //3、 创建连接
        Connection connection = factory.newConnection();
        //4、 新建管道
        Channel channel = connection.createChannel();
        //5、 接受信息
        DefaultConsumer consumer = new DefaultConsumer(channel){
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
        channel.basicConsume("workqueues", true, consumer);


    }
}